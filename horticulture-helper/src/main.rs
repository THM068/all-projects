use std::env::set_var;
use shuttle_runtime::SecretStore;
use sqlx::PgPool;
use crate::agent::agent::MyAgent;

mod errors;
mod models;
mod router;
mod routes;
mod templates;
mod agent;

#[shuttle_runtime::main]
async fn main(
    #[shuttle_shared_db::Postgres] pool: PgPool,
    #[shuttle_runtime::Secrets] secrets: SecretStore
) -> shuttle_axum::ShuttleAxum {
    secrets.into_iter().for_each(|(key, secret)| unsafe {
        println!("{}: {}", &key, &secret);
        set_var(key, secret);
    });
    sqlx::migrate!()
        .run(&pool)
        .await
        .expect("Failed to run migrations");

    let ai_agent = MyAgent::new();
    let router = router::init_router(pool, ai_agent);

    Ok(router.into())
}
