use crate::entities::prelude::*;
use crate::entities::role;
use crate::entities::role::{ActiveModel, Model};
use sea_orm::ActiveValue::Set;
use sea_orm::{
    ColumnTrait, DatabaseConnection, DbErr, DeleteResult, EntityTrait, InsertResult, QueryFilter,
};
use DbErr::{Custom, RecordNotFound};

pub struct Role_repository<'a> {
    pub db: &'a DatabaseConnection,
}

impl<'a> Role_repository<'a> {
    pub fn new(db: &'a DatabaseConnection) -> Role_repository {
        Role_repository { db }
    }

    pub async fn create(&self, name: String) -> Result<InsertResult<ActiveModel>, DbErr> {
        let role = ActiveModel {
            name: Set(name),
            ..Default::default()
        };
        Role::insert(role).exec(self.db).await
    }

    pub async fn find_one(&self, name: &String) -> Result<Option<Model>, DbErr> {
        Role::find()
            .filter(role::Column::Name.eq(name))
            .one(self.db)
            .await
    }

    pub async fn find_by_id(&self, id: i32) -> Result<Option<Model>, DbErr> {
        Role::find()
            .filter(role::Column::Id.eq(id))
            .one(self.db)
            .await
    }

    pub async fn find_all(&self) -> Result<Vec<Model>, DbErr> {
        Role::find().all(self.db).await
    }

    pub async fn delete_by_id(&self, id: i32) -> Result<(), DbErr> {
        let role = self.find_by_id(id).await?;
        let Some(model_role) = role else {
            return Err(RecordNotFound("Role".to_string()));
        };

        let active_role = ActiveModel {
            id: Set(model_role.id),
            ..Default::default()
        };

        return match Role::delete(active_role).exec(self.db).await {
            Ok(_) => Ok(()),
            Err(err) => Err(Custom("Error deleting role".to_string())),
        };
    }
}
