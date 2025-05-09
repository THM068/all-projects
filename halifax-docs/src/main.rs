#[macro_use]
extern crate rocket;

use crate::controller::user_management::not_authorised;
use crate::migrator::Migrator;
use controller::home;
use controller::user_management;
use rocket::fs::FileServer;
use rocket_dyn_templates::{context, Template};
use sea_orm::{DatabaseConnection, SqlxPostgresConnector};
use sea_orm_migration::MigratorTrait;
use shuttle_runtime::__internals::Context;
use std::env;
use crate::controller::minutes;

mod controller;
mod entities;
mod migrator;
mod repository;
mod util;
mod model;

#[shuttle_runtime::main]
async fn main(
    #[shuttle_runtime::Secrets] secrets: shuttle_runtime::SecretStore,
    #[shuttle_shared_db::Postgres] pool: sqlx::PgPool,
) -> shuttle_rocket::ShuttleRocket {
    let conn: DatabaseConnection = SqlxPostgresConnector::from_sqlx_postgres_pool(pool);
    Migrator::up(&conn, None).await.unwrap();

    let appConfig = AppConfig {
        jwt_secret: secrets.get("JWT_SECRET").context("secret was not found")?,
        cookie_domain: env::var("COOKIE_DOMAIN").unwrap_or(".wowtv.de".to_string()),
    };
    let rocket = rocket::build()
        .manage(conn)
        .manage(appConfig)
        // If you also wish to serve static content, uncomment line below and corresponding 'use' on line 4
        .mount("/static", FileServer::from("static/"))
        .mount(
            "/",
            routes![
                home::index,
                home::hello,
                user_management::show_register_user,
                user_management::register_user,
                user_management::show_register_success,
                user_management::sign_in,
                user_management::show_login,
                minutes::get_minutes,
                controller::members::get_members
            ],
        )
        .register("/", catchers![not_authorised])
        .attach(Template::fairing());

    Ok(rocket.into())
}
#[derive(Debug)]
struct AppConfig {
    pub jwt_secret: String,
    pub cookie_domain: String,
}
