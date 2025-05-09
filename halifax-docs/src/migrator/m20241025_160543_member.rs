use sea_orm_migration::prelude::*;

#[derive(DeriveMigrationName)]
pub struct Migration;

#[async_trait::async_trait]
impl MigrationTrait for Migration {
    async fn up(&self, manager: &SchemaManager) -> Result<(), DbErr> {
        // Replace the sample below with your own migration scripts

        manager
            .create_table(
                Table::create()
                    .table(Member::Table)
                    .if_not_exists()
                    .col(
                        ColumnDef::new(Member::Id)
                            .integer()
                            .not_null()
                            .auto_increment()
                            .primary_key(),
                    )
                    .col(ColumnDef::new(Member::Name).string().not_null())
                    .col(ColumnDef::new(Member::JoinedAt).date())
                    .col(ColumnDef::new(Member::ReasonOofJoining).string())
                    .col(ColumnDef::new(Member::PreviousChurch).string())
                    .col(
                        ColumnDef::new(Member::CreatedAt)
                            .timestamp()
                            .extra("DEFAULT CURRENT_TIMESTAMP".to_owned()),
                    )
                    .to_owned(),
            )
            .await
    }

    async fn down(&self, manager: &SchemaManager) -> Result<(), DbErr> {
        // Replace the sample below with your own migration scripts
        manager
            .drop_table(Table::drop().table(Member::Table).to_owned())
            .await
    }
}

#[derive(DeriveIden)]
enum Member {
    Table,
    Id,
    Name,
    JoinedAt,
    ReasonOofJoining,
    PreviousChurch,
    CreatedAt,
}
