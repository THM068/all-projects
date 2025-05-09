use sea_orm::{ColumnTrait, DatabaseConnection, DbErr, EntityTrait, InsertResult, QueryFilter};

use crate::entities::prelude::*;
use crate::entities::user;
use crate::entities::user::{ActiveModel, Model};

pub struct UserRepository<'a> {
    pub db: &'a DatabaseConnection,
}

impl<'a> UserRepository<'a> {
    pub fn new(db: &'a DatabaseConnection) -> UserRepository {
        UserRepository { db }
    }

    pub async fn create(&self, user: ActiveModel) -> Result<InsertResult<ActiveModel>, DbErr> {
        User::insert(user).exec(self.db).await
    }

    pub async fn find_one(&self, email: &String) -> Result<Option<Model>, DbErr> {
        User::find()
            .filter(user::Column::Email.eq(email))
            .one(self.db)
            .await
    }
}
