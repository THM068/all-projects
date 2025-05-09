#![allow(clippy::missing_errors_doc)]
#![allow(clippy::unnecessary_struct_initialization)]
#![allow(clippy::unused_async)]
use loco_rs::prelude::*;
use loco_rs::controller::Routes;
use axum::Form;
use axum::routing::post;
use loco_rs::prelude::*;
use serde::{Deserialize, Serialize};
use axum::extract::State;
use axum::response::Response;
use crate::models::_entities::subscriptions::ActiveModel;

pub async fn subscription(
    State(ctx): State<AppContext>,
    Form(subscription): Form<Subscription>,
) -> loco_rs::Result<Response> {
    let subscription_active_model = ActiveModel {
        email: Set(subscription.email),
        terms: Set(Some(subscription.terms)),
        ..Default::default()
    };
    let result = subscription_active_model.save(&ctx.db).await;
    let Ok(r) = result else {
        return format::text(r#"<p style="color:red">Email already exist</p>"#);
    };
    println!("the id is {:?}", r.last_insert_id);
    format::text(r#"<p style="color:white">Subscribed successfully</p>"#)

}

pub fn routes() -> Routes {
    Routes::new()
        .prefix("subscribe")
        .add("/", post(subscription))
}

#[derive(Debug, Deserialize, Serialize)]
struct Subscription {
    pub email: String,
    pub terms: bool,
}
