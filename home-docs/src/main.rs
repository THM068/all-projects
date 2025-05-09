mod agent;
mod routes;

use crate::agent::summarize_video;
use askama::Template;
use axum::extract::Path;
use axum::http::StatusCode;
use axum::response::{Html, IntoResponse, Response};
use axum::{routing::get, Router};
use tower_http::services::ServeDir;

async fn summarize_endpoint(Path(video): Path<String>) -> String {
    let summary = summarize_video(video.as_str()).await;
    return summary;
}

async fn hello_world() -> impl IntoResponse {
    let hello = HelloTemplate;

    HtmlTemplate(hello)
}

#[shuttle_runtime::main]
async fn main() -> shuttle_axum::ShuttleAxum {
    let router = Router::new()
        .route("/", get(hello_world))
        .route("/summarize/:video", get(summarize_endpoint))
        .nest_service("/assets", ServeDir::new("static"));

    Ok(router.into())
}

#[derive(Template)]
#[template(path = "index.html")]
struct HelloTemplate;

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
