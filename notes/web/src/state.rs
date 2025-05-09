use notes_config::Config;

use std::sync::Arc;
use sea_orm::{Database, DatabaseConnection};

/// The application's state that is available in [`crate::controllers`] and [`crate::middlewares`].
pub struct AppState {
    pub(crate) name: String,
    pub(crate) db: DatabaseConnection,
}

/// The application's state as it is shared across the application, e.g. in controllers and middlewares.
///
/// This is the [`AppState`] struct wrappend in an [`std::sync::Arc`].
pub type SharedAppState = Arc<AppState>;

/// Initializes the application state.
///
/// This function creates an [`AppState`] based on the current [`notes_config::Config`].

pub async fn init_app_state(_config: Config) -> AppState {
    const DATABASE_URL: &str = "sqlite://notes_development.sqlite?mode=rwc";
    let db: DatabaseConnection = Database::connect(DATABASE_URL).await.unwrap();
    AppState {
        name: "Some Config".to_owned(),
        db,
    }
}
