use sea_orm::{DeleteResult, InsertResult};
use sea_orm::entity::prelude::*;

use super::_entities::subscriptions::{ActiveModel, Entity, Model};

pub type Subscriptions = Entity;

impl ActiveModelBehavior for ActiveModel {
    // extend activemodel below (keep comment for generators)
}

impl super::_entities::subscriptions::ActiveModel {
    pub async fn save(self, db: &DatabaseConnection) -> Result<InsertResult<ActiveModel>, DbErr> {
        Subscriptions::insert(self).exec(db).await
    }

    pub async fn update(self, db: &DatabaseConnection) -> Result<Model, DbErr> {
       Subscriptions::update(self).exec(db).await
    }

    pub async fn delete(model: ActiveModel, db: &DatabaseConnection) -> Result<DeleteResult, DbErr> {
        model.delete(db).await
    }
}

impl super::_entities::subscriptions::Model {
    pub async fn find_by_id(id: i32, db: &DatabaseConnection) -> Result<Option<Model>, DbErr> {
        Subscriptions::find_by_id(id).one(db).await
    }

    pub async fn find_all(db: &DatabaseConnection) -> Result<Vec<Model>, DbErr> {
        Subscriptions::find().all(db).await
    }
}
