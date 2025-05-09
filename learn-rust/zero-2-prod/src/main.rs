mod views;
mod controllers;
mod models;

use std::collections::HashMap;
use std::sync::{Arc, Mutex};
use crate::views::APP_PREFIX;
use actix_web::{get, web, App, HttpResponse, HttpServer, Responder};
use actix_web::middleware::Logger;
use env_logger::Env;
use crate::models::Subscriber;

#[get("/greet")]
async fn greet() -> impl Responder {
    HttpResponse::Ok().body("Hello world!")
}



async fn index(data: web::Data<AppState>) -> impl Responder {
    let app_name = &data.name;
    format!("Hello world! {}", app_name)
}
type SubscriberDb = Arc<Mutex<HashMap<String, Subscriber>>>;

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    let port = 9090;
    println!("Starting server at http://localhost:{}", port);
    let subscriber_db: SubscriberDb = Arc::new(Mutex::new(HashMap::new()));
    env_logger::Builder::from_env(Env::default().default_filter_or("info")).init();

    HttpServer::new(move || {
        let subscriber_app_date= web::Data::new(subscriber_db.clone());
        App::new()
            .app_data(web::Data::new(AppState {
                name: "Actix-web".to_string(),
            }))
            .wrap(Logger::default())
            .app_data(subscriber_app_date)
            .service(greet)
            .service(controllers::healthcheck::health_check)
            .service(controllers::subscriptions::subscribe)
            .service(controllers::subscriptions::get_subscriptions)
            .service(web::scope(APP_PREFIX).route("/index.html", web::get().to(index)))
    })
    .bind(("127.0.0.1", 9090))?
    .run()
    .await
}

struct AppState {
    name: String,
}
