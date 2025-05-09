use std::sync::Arc;
use axum::response::Json;
use axum::{Router, routing};
use axum::extract::State;
use routing::get;
use serde::{Deserialize, Serialize};
use crate::state::AppState;

/// A greeting to respond with to the requesting client
#[derive(Deserialize, Serialize)]
pub struct Greeting {
    /// Who do we say hello to?
    pub hello: String,
}

pub fn greeting_routes(shared_state: Arc<AppState>) -> Router {
    /// Responds with a [`Greeting`], encoded as JSON.
    #[axum::debug_handler]
    pub async fn hello(State(_state): State<Arc<AppState>>) -> Json<Greeting> {
        Json(Greeting {
            hello: String::from("world "),
        })
    }

    Router::new()
        .route("/greet", get(hello))
        .with_state(shared_state)
}
