pub use sea_orm_migration::prelude::*;

mod m20241013_045537_user;
mod m20241016_212301_role;
mod m20241017_222418_role_user;
mod m20241025_160543_member;

pub struct Migrator;

#[async_trait::async_trait]
impl MigratorTrait for Migrator {
    fn migrations() -> Vec<Box<dyn MigrationTrait>> {
        vec![
            Box::new(m20241013_045537_user::Migration),
            Box::new(m20241016_212301_role::Migration),
            Box::new(m20241017_222418_role_user::Migration),
            Box::new(m20241025_160543_member::Migration),
        ]
    }
}
