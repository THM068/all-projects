#![allow(elided_lifetimes_in_paths)]
#![allow(clippy::wildcard_imports)]
pub use sea_orm_migration::prelude::*;

mod m20220101_000001_users;

mod m20241113_221508_movies;
mod m20241117_145512_subscriptions;
pub struct Migrator;

#[async_trait::async_trait]
impl MigratorTrait for Migrator {
    fn migrations() -> Vec<Box<dyn MigrationTrait>> {
        vec![
            // inject-below (do not remove this comment)
            Box::new(m20241117_145512_subscriptions::Migration),
            Box::new(m20241113_221508_movies::Migration),
            Box::new(m20220101_000001_users::Migration),
            // inject-above (do not remove this comment)
        ]
    }
}