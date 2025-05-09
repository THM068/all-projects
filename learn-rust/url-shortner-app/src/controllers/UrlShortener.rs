use axum::Router;
use axum::routing::get;

pub fn url_shortener() -> Router {
    async fn create_short_url() -> &'static str {
        "Create short URL"
    }

   async fn get_all_short_urls() -> &'static str {
        "Get all short URLs"
    }

    Router::new()
        .route("/", get(get_all_short_urls)
                         .post(create_short_url))
}