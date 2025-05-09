use loco_rs::prelude::*;

pub fn render_index(v: &impl ViewRenderer) -> Result<Response> {
    format::render().view(v, "home/index.html", data!({}))
}