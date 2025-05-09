use async_openai::config::OpenAIConfig;
use async_openai::error::OpenAIError;
use async_openai::types::{
    ChatCompletionRequestMessage, ChatCompletionRequestSystemMessageArgs,
    ChatCompletionRequestUserMessageArgs, CreateChatCompletionRequestArgs,
};
use async_openai::Client;
use serde::Deserialize;
use youtube_captions::format::Format;
use youtube_captions::language_tags::LanguageTag;
use youtube_captions::DigestScraper;

static SUMMARY_PROMPT: &str = r#"You are an agent dedicated to summarising video transcripts.
You will receive a transcript and answer with main talking points of the video first,
followed by a complete summary of the transcript. Answer only in this format:


Talking points:
1. ..
2. ..
N. ..

Summary:
Summary of the transcript
"#;

static SUMMARY_TO_JSON_PROMPT: &str = r#"You are an agent dedicated to translating text to JSON. You will receive the text and return it in JSON format.
The format is as follows:


{

    “summary”: “Whole video summary goes here”,
   “talking_points”: [
{
   “title” : “Title of the point”,
   “description: “Talking point summary”
 },
...
]
}

Rules:
- Follow the specified JSON format closely
- Wrap the JSON in a code block
- Skip prose, return only the JSON
"#;

pub(crate) enum Role {
    AGENT,
    USER,
    SYSTEM,
}

pub(crate) struct Message {
    pub(crate) content: String,
    pub(crate) role: Role,
}

pub(crate) struct Agent {
    pub(crate) system: String,
    pub(crate) model: String,
    pub(crate) history: Vec<Message>,
    pub(crate) client: Client<OpenAIConfig>,
}

impl Agent {
    async fn prompt(&mut self, input: String) -> Result<String, OpenAIError> {
        //If you are remembering the message history, you can restore it from here.

        self.client.chat().create(
            CreateChatCompletionRequestArgs::default()
                .model(self.model.clone())
                .messages(vec![

                    //First we add the system message to define what the Agent does
                    ChatCompletionRequestMessage::System(
                        ChatCompletionRequestSystemMessageArgs::default()
                            .content(&*self.system)
                            .build()
                            .unwrap(),
                    ),

                    //Then we add our prompt
                    ChatCompletionRequestMessage::User(
                        ChatCompletionRequestUserMessageArgs::default()
                            .content(input)
                            .build()
                            .unwrap(),
                    ),
                ])
                .build()
                .unwrap(),
        ).await.map(|res| {
            //We extract the first one
            res.choices[0].message.content.clone().unwrap()
        })

        //Now here, you can save the prompt and agent response to the history if needed
    }
}

async fn get_transcript(video: &str) -> String {
    let digest = DigestScraper::new(reqwest::Client::new());
    // Fetch the video
    let scraped = digest.fetch(video, "en").await.unwrap();

    // Find our preferred language - in this case, english
    let language = LanguageTag::parse("en").unwrap();

    let captions = scraped
        .captions
        .into_iter()
        .find(|caption| language.matches(&caption.lang_tag))
        .unwrap();

    let transcript_json = captions.fetch(Format::JSON3).await.unwrap();

    let root: Transcript = serde_json::from_str(transcript_json.as_str()).unwrap();

    // Collect all utf8 fields from all events and all segments
    let transcript: String = root
        .events
        .iter()
        .filter_map(|event| event.segs.as_ref())
        .flatten()
        .map(|segment| segment.utf8.clone()) // Extract the utf8 field of each segment
        .collect::<Vec<String>>()
        .join(" ");
    return transcript;
}

#[derive(Deserialize)]
struct Transcript {
    events: Vec<Event>,
}
#[derive(Deserialize)]
struct Event {
    segs: Option<Vec<Segment>>,
}

#[derive(Deserialize)]
struct Segment {
    utf8: String,
}

pub async fn summarize_video(video: &str) -> String {
    let client = Client::with_config(OpenAIConfig::default());

    //First, we fetch the transcript for the video
    let transcript = get_transcript(video).await;

    // Then we create our summary agent and have it summarize the video for us
    let mut summarize_agent = Agent {
        system: SUMMARY_PROMPT.to_string(),
        model: "gpt-4o-mini".to_string(),
        history: vec![],
        client: client.clone(),
    };

    let summary = summarize_agent.prompt(transcript).await.unwrap();
    //In summarize_video
    let mut summary_to_json_agent = Agent {
        system: SUMMARY_TO_JSON_PROMPT.to_string(),
        client: client.clone(),
        model: "gpt-4o-mini".to_string(),
        history: vec![],
    };

    let json = summary_to_json_agent.prompt(summary).await.unwrap();
    let result = extract_codeblock(&json);
    return result;
}

pub fn extract_codeblock(text: &str) -> String {
    if !text.contains("```") {
        return text.to_string();
    }
    let mut in_codeblock = false;
    let mut extracted_lines = vec![];

    for line in text.lines() {
        if line.trim().starts_with("```") {
            in_codeblock = !in_codeblock;
            continue;
        }

        if in_codeblock {
            extracted_lines.push(line);
        }
    }

    extracted_lines.join("\n")
}
