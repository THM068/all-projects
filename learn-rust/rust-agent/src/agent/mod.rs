pub(crate) enum Role {
    AGENT,
    USER,
    SYSTEM
}

pub(crate) struct Message {
    pub(crate) content: String,
    pub(crate) role: Role
}

pub(crate) struct Agent {
    pub(crate) system: String,
    pub(crate) model: String,
    pub(crate) history: Vec<Message>
}

impl Agent {
    async fn prompt(&mut self, input: String) -> Result<String, Error> {
       OK("Hello, world!")
    }
}