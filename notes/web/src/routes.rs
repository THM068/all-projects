use std::sync::Arc;

use axum::Router;
use tower_http::services::ServeDir;

use greeting::greeting_routes;

use crate::controllers::greeting;
use crate::controllers::home::home_routes;
use crate::state::AppState;

/// Initializes the application's routes.
///
/// This function maps paths (e.g. "/greet") and HTTP methods (e.g. "GET") to functions in [`crate::controllers`] as well as includes middlewares defined in [`crate::middlewares`] into the routing layer (see [`axum::Router`]).
pub fn init_routes(app_state: AppState) -> Router {
    let shared_app_state = Arc::new(app_state);

    Router::new()
        .merge(greeting_routes(Arc::clone(&shared_app_state)))
        .merge(home_routes(Arc::clone(&shared_app_state)))
        .nest_service("/assets", ServeDir::new("static"))
}
