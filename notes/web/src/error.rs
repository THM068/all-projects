use axum::{http::StatusCode, response::IntoResponse};
use std::fmt::{Debug, Display};

/// Error type that encapsultes anything that can go wrong
/// in this application. Implements [IntoResponse],
/// so that it can be returned directly from a request handler.
#[derive(thiserror::Error, Debug)]
pub enum Error {
    /// Any other error. Handled as an Internal Server Error.
    #[error("Error: {0}")]
    Other(#[from] anyhow::Error),
}

impl IntoResponse for Error {
    fn into_response(self) -> axum::response::Response {
        match self {
            Error::Other(e) => internal_error(e).into_response(),
        }
    }
}

/// Helper function to create an internal error response while
/// taking care to log the error itself.
fn internal_error<E>(e: E) -> StatusCode
where
    // Some "error-like" types (e.g. `anyhow::Error`) don't implement the error trait, therefore
    // we "downgrade" to simply requiring `Debug` and `Display`, the traits
    // we actually need for logging purposes.
    E: Debug + Display,
{
    tracing::error!(err.msg = %e, err.details = ?e, "Internal server error");
    // We don't want to leak internal implementation details to the client
    // via the error response, so we just return an opaque internal server.
    StatusCode::INTERNAL_SERVER_ERROR
}
