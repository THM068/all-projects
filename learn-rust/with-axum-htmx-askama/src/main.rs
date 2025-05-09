mod controllers;
mod model;

use std::sync::{Arc, Mutex};
use askama::{ Template};
use axum::http::StatusCode;
use axum::response::{Html, IntoResponse, Response};
use axum::Router;
use tower_http::services::ServeDir;
use tracing::info;
use tracing_subscriber::layer::SubscriberExt;
use tracing_subscriber::util::SubscriberInitExt;
use controllers::index_controller::index_routes;
use crate::controllers::index_controller::{AppState, Config_State, Todo_state};
use crate::controllers::url_shortner::url_shortner_routes;

#[tokio::main]
async fn main() -> anyhow::Result<()>{
    tracing_subscriber::registry()
        .with(
            tracing_subscriber::EnvFilter::try_from_default_env()
                .unwrap_or_else(|_| "with_axum_htmx_askama=debug".into()),
        )
        .with(tracing_subscriber::fmt::layer())
        .init();

    let app_state = Arc::new(
        AppState {
            todo_state: Todo_state { todos: Mutex::new(vec![])},
            config_state: Config_State {
                name: "Some Config".to_owned()
            }
        }
    );

    info!("Initializing routers!");
    let port = 9900_u16;

    info!("router initialized, now listening on port http://localhost:{}", port);

    let app = Router::new()
        .merge(index_routes(app_state))
        .nest_service("/url-shortener", url_shortner_routes())
        .nest_service("/assets", ServeDir::new("static"));


    let listener = tokio::net::TcpListener::bind(format!("0.0.0.0:{}",port)).await.unwrap();
    axum::serve(listener, app).await.unwrap();

    Ok(())
}


/// A wrapper type that we'll use to encapsulate HTML parsed by askama into valid HTML for axum to serve.
struct HtmlTemplate<T>(T);

/// Allows us to convert Askama HTML templates into valid HTML for axum to serve in the response.
impl<T> IntoResponse for HtmlTemplate<T>
    where
        T: Template,
{
    fn into_response(self) -> Response {
        // Attempt to render the template with askama
        match self.0.render() {
            // If we're able to successfully parse and aggregate the template, serve it
            Ok(html) => Html(html).into_response(),
            // If we're not, return an error or some bit of fallback HTML
            Err(err) => (
                StatusCode::INTERNAL_SERVER_ERROR,
                format!("Failed to render template. Error: {}", err),
            )
                .into_response(),
        }
    }
}

struct Url_Shortener_model {
    id: String,
    url: String,
}




