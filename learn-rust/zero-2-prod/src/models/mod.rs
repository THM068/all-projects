use chrono::{DateTime, Utc};
use serde::{Deserialize, Serialize};

pub struct Subscriber{
    pub id: String,
    pub email: String,
    pub name: String,
    pub date_created: DateTime<Utc>
}
#[derive(Serialize, Deserialize)]
pub struct SubscriberResponse {
    pub id: String,
    pub email: String,
    pub name: String
}

