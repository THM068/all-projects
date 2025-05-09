use sea_orm_migration::prelude::*;
use crate::migrator::m20241013_045537_user::User;
use crate::migrator::m20241016_212301_role::Role;

use super::m20241013_045537_user;
use super::m20241016_212301_role;
#[derive(DeriveMigrationName)]
pub struct Migration;

#[async_trait::async_trait]
impl MigrationTrait for Migration {
    async fn up(&self, manager: &SchemaManager) -> Result<(), DbErr> {
        // Replace the sample below with your own migration scripts

        manager
            .create_table(
                Table::create()
                    .table(RoleUser::Table)
                    .if_not_exists()
                    .col(
                        ColumnDef::new(RoleUser::Id)
                            .integer()
                            .not_null()
                            .auto_increment()
                            .primary_key(),
                    )
                    .col(ColumnDef::new(RoleUser::RoleId).string().not_null())
                    .foreign_key(
                        ForeignKey::create()
                            .name("fk-role-user-role_id")
                            .from(RoleUser::Table, RoleUser::RoleId)
                            .to(Role::Table, Role::Id),
                    )
                    .col(ColumnDef::new(RoleUser::UserId).string().not_null())
                    .foreign_key(
                        ForeignKey::create()
                            .name("fk-role-user-user_id")
                            .from(RoleUser::Table, RoleUser::UserId)
                            .to(User::Table, User::Id),
                    )
                    .to_owned(),
            )
            .await
    }

    async fn down(&self, manager: &SchemaManager) -> Result<(), DbErr> {
        // Replace the sample below with your own migration scripts
        manager
            .drop_table(Table::drop().table(RoleUser::Table).to_owned())
            .await
    }
}

#[derive(DeriveIden)]
enum RoleUser {
    Table,
    Id,
    RoleId,
    UserId,
}
