use crate::models::TodoUpdate;
use axum::{
    routing::{delete, get},
    Extension, Router,
};
use sqlx::PgPool;

use crate::routes;
use tokio::sync::broadcast::{channel, Sender};
use crate::agent::agent::MyAgent;

pub type TodosStream = Sender<TodoUpdate>;

#[derive(Clone)]
pub struct AppState {
    pub db: PgPool,
    pub agent: MyAgent
}

pub fn init_router(db: PgPool, ai_agent: MyAgent) -> Router {
    let (tx, _rx) = channel::<TodoUpdate>(10);
    let state = AppState { db: db ,  agent: ai_agent };

    Router::new()
        .route("/", get(routes::home))
        .route("/stream", get(routes::stream))
        .route("/styles.css", get(routes::styles))
        .route("/todos", get(routes::fetch_todos).post(routes::create_todo))
        .route("/todos/:id", delete(routes::delete_todo))
        .route("/todos/stream", get(routes::handle_stream))
        .route("/chat", get(routes::chat))
        .with_state(state)
        .layer(Extension(tx))
}
