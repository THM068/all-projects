use axum::extract::{Path, State};
use axum::{Extension, Json, Router, routing};
use axum::http::StatusCode;
use axum::middleware::from_fn_with_state;
use axum::routing::{delete, get};
use routing::post;
use uuid::Uuid;
use uptime_api_db::entities;
use uptime_api_db::entities::users::User;
use crate::{error::Error, state::SharedAppState};
use crate::middlewares::validate_jwt::validate_jwt;


pub fn monitored_websites_routes(shared_state: SharedAppState) -> Router {
    #[axum::debug_handler]
    pub async fn create(
        State(app_state): State<SharedAppState>,
        Extension(currentUser): Extension<User>,
        Json(monitoredwebsite): Json<(entities::monitoredwebsites::MonitoredwebsiteChangeset)>,
    ) -> Result<(StatusCode, Json<entities::monitoredwebsites::Monitoredwebsite>), Error> {
        let monitored_site = entities::monitoredwebsites::create(monitoredwebsite, &app_state.db_pool).await?;
        Ok((StatusCode::CREATED, Json(monitored_site)))
    }

    #[axum::debug_handler]
    pub async fn delete_monitored_website(
        State(app_state): State<SharedAppState>,
        Path(id): Path<Uuid>,
    ) -> Result<StatusCode, Error> {
        entities::monitoredwebsites::delete(id, &app_state.db_pool).await?;
        Ok(StatusCode::NO_CONTENT)
    }

    #[axum::debug_handler]
    pub async fn get_monitored_website(
        State(app_state): State<SharedAppState>,
        Path(id): Path<Uuid>,
        Extension(currentUser): Extension<User>
    ) -> Result<Json<entities::monitoredwebsites::Monitoredwebsite>, Error> {
        let monitored_site = entities::monitoredwebsites::load(id, &app_state.db_pool).await?;
        Ok(Json(monitored_site))
    }

    Router::new()
        .route("/monitored-website", post(create).layer(from_fn_with_state(shared_state.clone(), validate_jwt)))
        // .route("/", get(get_all_monitored_websites))
        .route("/monitored-website/:id", get(get_monitored_website).layer(from_fn_with_state(shared_state.clone(), validate_jwt)))
        //.route("/:id", put(update_monitored_website))
        .route("/monitored-website/:id", delete(delete_monitored_website).layer(from_fn_with_state(shared_state.clone(), validate_jwt)))
       .with_state(shared_state)
}

#[derive(Debug, serde::Deserialize, serde::Serialize)]
struct MonitoredWebsite_Dto {
    pub user_id: Option<Uuid>,
    pub url: String,
    pub check_interval: Option<i32>,
    pub latency_threshold: Option<i32>,
    pub consecutive_failures_threshold: Option<i32>,
}