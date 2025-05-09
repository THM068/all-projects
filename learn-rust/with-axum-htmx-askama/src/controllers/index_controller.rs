use std::sync::{Arc, Mutex};
use askama::Template;
use axum::http::StatusCode;
use axum::extract::{FromRef, Query, State};
use axum::response::{Html, IntoResponse, Response};
use axum::{Form, Router};
use axum::routing::{get, post};
use serde::{Deserialize, Serialize};
use crate::HtmlTemplate;


pub fn index_routes(application_state: Arc<AppState>) -> Router {
    async fn hello() -> impl IntoResponse {
        let template = HelloTemplate {
            name: "Thando Mafela"
        };
        HtmlTemplate(template)
    }

    async fn another_page() -> impl IntoResponse {
        let template = AnotherPageTemplate {};
        HtmlTemplate(template)
    }

    async fn hello_params(Query(params): Query<Hello_Params>) -> impl IntoResponse {
        let name = params.name.as_deref().unwrap_or("Alexandra");
        Html(format!("Hello <strong>{name}</strong>"))
    }

    async fn add_todos(State(state): State<Arc<AppState>>,
                       Form(todo): Form<TodoRequest>) -> impl IntoResponse {
        let mut lock = state.todo_state.todos.lock().unwrap();
        lock.push(todo.todo);

        let template = TodoList {
            todos: lock.clone(),
        };

        HtmlTemplate(template)
    }

    async fn health_check() -> impl IntoResponse {
        (StatusCode::OK,"Application Up and Running!")
    }

    Router::new()
        .route("/", get(hello))
        .route("/health-check", get(health_check))
        .route("/another-page",get(another_page))
        .route("/params", get(hello_params))
        .route("/api/todos", post(add_todos))
        .with_state(application_state)

}

#[derive(Template)]
#[template(path = "hello.html")]
struct HelloTemplate<'a> {
    name: &'a str
}

#[derive(Template)]
#[template(path = "another-page.html")]
struct AnotherPageTemplate;
#[derive(Debug,Deserialize)]
struct Hello_Params {
    name: Option<String>
}

pub(crate) struct Todo_state {
    pub(crate) todos: Mutex<Vec<String>>
}
pub(crate) struct AppState {
    pub(crate) todo_state: Todo_state,
    pub(crate) config_state: Config_State
}

pub(crate) struct Config_State {
    pub(crate) name: String
}

#[derive(Debug, Serialize, Deserialize)]
struct TodoRequest {
    todo: String
}

#[derive(Template)]
#[template(path = "todo-list.html")]
struct TodoList {
    todos: Vec<String>,
}

