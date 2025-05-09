use axum::debug_handler;
use axum::extract::State;
use axum::response::Response;
use axum::routing::get;
use loco_rs::app::AppContext;
use loco_rs::controller::Routes;
use loco_rs::prelude::{TeraView, ViewEngine};

use crate::views;

#[debug_handler]
pub async fn home(
    ViewEngine(v): ViewEngine<TeraView>,
    State(_ctx): State<AppContext>,
) -> loco_rs::Result<Response> {
    views::home::render_index(&v)
}

pub fn routes() -> Routes {
    Routes::new()
        .add("/", get(home))
}

