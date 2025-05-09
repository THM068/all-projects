use sea_orm::{ActiveModelTrait, DatabaseConnection, DbErr, DeleteResult, EntityTrait, InsertResult};
use crate::entities::prelude::User;
use crate::entities::user::{ActiveModel, Model};

impl crate::entities::user::ActiveModel {
    pub async fn save(self, db: &DatabaseConnection) -> Result<InsertResult<ActiveModel>, DbErr> {
        User::insert(self).exec(db).await
    }

    pub async fn update(self, db: &DatabaseConnection) -> Result<Model, DbErr> {
        User::update(self).exec(db).await
    }

    pub async fn delete(model: ActiveModel, db: &DatabaseConnection) -> Result<DeleteResult, DbErr> {
        model.delete(db).await
    }
}

impl crate::entities::user::Model {

}