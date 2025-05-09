use askama::Template;
use crate::{error::Error, state::SharedAppState};
use axum::{extract::State, http::StatusCode, Router, routing};
use axum::response::IntoResponse;
use tracing::info;
use crate::controllers::view::HtmlTemplate;

#[derive(Template)]
#[template(path = "index.html")]
struct IndexPageTemplate;

pub fn home_routes(shared_state: SharedAppState) -> Router {

    #[axum::debug_handler]
    pub async fn home(State(_app_state): State<SharedAppState>) -> impl IntoResponse {

        info!("responding with {:?}", StatusCode::OK);

        let template = IndexPageTemplate {};
        HtmlTemplate(template)
    }

    Router::new()
        .route("/", routing::get(home))
        .with_state(shared_state)
}
