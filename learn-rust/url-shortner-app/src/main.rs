use axum::{Extension, extract, Router, routing::get};
use axum::extract::FromRef;
use axum_template::engine::Engine;
use axum_template::TemplateEngine;
use handlebars::{DirectorySourceOptions, Handlebars};
use shuttle_runtime::__internals::serde_json::json;

mod controllers;


async fn hello_world() ->  &'static str  {
    "Hello world"
}

#[shuttle_runtime::main]
async fn main() -> shuttle_axum::ShuttleAxum {
    let mut hbs = Handlebars::new();
    hbs.register_templates_directory("templates", DirectorySourceOptions {
        tpl_extension: ".hbs".into(),
        hidden: false,temporary: false
    }).unwrap();
    let router = Router::new()
        .route("/", get(hello_world)
                         .post(hello_world))
        .nest("/url-shortener", controllers::UrlShortener::url_shortener());


    Ok(router.into())
}

