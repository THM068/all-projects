use async_openai::{Client as OpenAIClient, config::OpenAIConfig};
use async_openai::types::{
    ChatCompletionRequestMessage, ChatCompletionRequestSystemMessageArgs,
    ChatCompletionRequestUserMessageArgs, CreateChatCompletionRequestArgs,
};

static SYSTEM_MESSAGE: &str = "You are a knowledgeable historian. Answer the following question accurately and concisely.";

static PROMPT_MODEL: &str = "deepseek-chat";//"gpt-4o";

#[derive(Clone)]
pub struct MyAgent {
    pub openai_client: OpenAIClient<OpenAIConfig>,
}

impl MyAgent {
    pub fn new() -> Self {
        let api_key = std::env::var("OPENAI_API_KEY").unwrap();
        let base_url = std::env::var("OPENAI_BASE_URL").unwrap_or("https://api.openai.com/v1".to_string());
        let config = OpenAIConfig::new()
            .with_api_key(api_key)
            .with_api_base(base_url);

        let openai_client = OpenAIClient::with_config(config);

        Self {
            openai_client
        }
    }

    pub async fn prompt(&self, input: &str) -> anyhow::Result<String> {
        let result = self.openai_client.chat().create(
            CreateChatCompletionRequestArgs::default()
                .model(PROMPT_MODEL)
                .messages(vec![
                    //First we add the system message to define what the Agent does
                    ChatCompletionRequestMessage::System(
                        ChatCompletionRequestSystemMessageArgs::default()
                            .content(SYSTEM_MESSAGE)
                            .build()?,
                    ),
                    //Then we add our prompt
                    ChatCompletionRequestMessage::User(
                        ChatCompletionRequestUserMessageArgs::default()
                            .content(input)
                            .build()?,
                    ),
                ])
                .build()?,
        ).await
            .map(|res| {
                //We extract the first one
                res.choices[0].message.content.clone().unwrap()
            })?;

        Ok(result)

    }

}