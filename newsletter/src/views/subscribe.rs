use axum::response::Response;
use loco_rs::controller::format;
use loco_rs::prelude::{data, ViewRenderer};

pub fn render_index(v: &impl ViewRenderer) -> loco_rs::Result<Response> {
    format::render().view(v, "home/index.html", data!({}))
}