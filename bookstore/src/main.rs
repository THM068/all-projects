#[macro_use] extern crate rocket;

use rocket::http::Status;
use sea_orm::DatabaseConnection;
use sea_orm_migration::MigratorTrait;
use fairings::cors::{options, Cors};
use crate::controllers::{Response, SuccessResponse};
use crate::migrator::Migrator;

mod migrator;
mod db;
mod entities;
mod controllers;
mod fairings;

#[get("/")]
fn index() -> Response<String> {
   Ok(SuccessResponse((Status::Ok, "Welcome to the Bookstore API".to_string())))
}
#[launch]
async fn rocket() -> _ {
 dotenvy::dotenv().ok();
 let app_config: AppConfig = AppConfig::default();
 let db: DatabaseConnection = match db::connect(&app_config).await {
     Ok(db) => db,
     Err(err) => {
         panic!("Failed to connect to the database: {:?}", err);
     }
 };
    
  Migrator::up(&db, None).await.unwrap();
 rocket::build()
     .attach(Cors)
     .manage(db)
     .manage(app_config)
     .mount("/", routes![index])
     .mount("/", routes![options])
     .mount("/auth", routes![
         controllers::auth::sign_in,
         controllers::auth::sign_up,
     ])
     .mount("/books", routes![
         controllers::books::index,
         controllers::books::create,
         controllers::books::delete,
         controllers::books::show,
         controllers::books::update,
     ])
     .mount("/authors", routes![
         controllers::authors::index,
         controllers::authors::create,
         controllers::authors::delete,
         controllers::authors::show,
         controllers::authors::update,
     ])
 }

pub struct AppConfig {
    db_host: String,
    db_port: String,
    db_username: String,
    db_password: String,
    db_database: String,
    jwt_secret: String,
}
impl Default for AppConfig {
    fn default() -> Self {
        Self {
            db_host: std::env::var("BOOKSTORE_DB_HOST").unwrap_or("localhost".to_string()),
            db_port: std::env::var("BOOKSTORE_DB_PORT").unwrap_or("3306".to_string()),
            db_username: std::env::var("BOOKSTORE_DB_USERNAME").unwrap_or("root".to_string()),
            db_password: std::env::var("BOOKSTORE_DB_PASSWORD").unwrap_or("".to_string()),
            db_database: std::env::var("BOOKSTORE_DB_DATABASE").unwrap_or("bookstore".to_string()),
            jwt_secret: std::env::var("BOOKSTORE_JWT_SECRET")
                .expect("BOOKSTORE_JWT_SECRET must be set")
        }
    }
}