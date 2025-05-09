use crate::HtmlTemplate;
use askama::Template;
use axum::extract::Path;
use axum::response::IntoResponse;
use axum::routing::get;
use axum::{Form, Router};
use log::info;
use nanoid::nanoid;
use serde::{Deserialize, Serialize};

pub fn url_shortner_routes() -> Router {
    async fn index() -> impl IntoResponse {
        let template = Index_Url_Shortener;
        HtmlTemplate(template)
    }

    async fn list() -> impl IntoResponse {
        "list all urls and ids"
    }

    async fn open_url(Path(url_id): Path<String>) -> impl IntoResponse {
        info!("Opening id {url_id}");
        "open url"
    }

    async fn add_new_url(Form(client_url): Form<Client_Url>) -> impl IntoResponse {
        let id = nanoid!();
        format!("saving the new url {:?}", client_url)
    }

    Router::new()
        .route("/", get(index).post(add_new_url))
        .route("/list", get(list))
        .route("/:url_id", get(open_url))
}

#[derive(Template)]
#[template(path = "url-shortener/index.html")]
struct Index_Url_Shortener;

#[derive(Debug, Deserialize, Serialize)]
pub struct Client_Url {
    pub url: String,
}
