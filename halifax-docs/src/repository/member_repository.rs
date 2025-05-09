use sea_orm::ActiveValue::Set;
use sea_orm::{DatabaseConnection, DbErr, EntityTrait, InsertResult};
use sea_orm::prelude::{Date, DateTime};
use crate::entities::member::ActiveModel;
use crate::entities::prelude::Member;

struct Member_repository<'a> {
    pub db: &'a DatabaseConnection,
}

impl<'a> Member_repository<'a> {
    pub fn new(db: &'a DatabaseConnection) -> Member_repository {
        Member_repository { db }
    }

    pub async fn create(&self, name: String, joined_at: Option<Date>, reason_oof_joining: Option<String>, previous_church: Option<String>, created_at: Option<DateTime>) -> Result<InsertResult<ActiveModel>, DbErr> {
        let member = ActiveModel {
            name: Set(name),
            joined_at: Set(joined_at),
            reason_oof_joining: Set(reason_oof_joining),
            previous_church: Set(previous_church),
            created_at: Set(created_at),
            ..Default::default()
        };
        Member::insert(member).exec(self.db).await
    }
}