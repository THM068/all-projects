use sea_orm::entity::prelude::*;
use super::_entities::movies::{ActiveModel, Entity};
pub type Movies = Entity;

impl ActiveModelBehavior for ActiveModel {
    // extend activemodel below (keep comment for generators)
}
