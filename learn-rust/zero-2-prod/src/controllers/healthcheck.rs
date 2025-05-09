use actix_web::{get, HttpResponse, Responder};

#[get("/health_check")]
async fn health_check() -> impl Responder {
    HttpResponse::Ok()
}

#[cfg(test)]
mod tests {
    use super::*;
    use actix_web::{test, App};

    #[tokio::test]
    async fn test_health_check() {

        let mut app = test::init_service(App::new().service(health_check)).await;
        let req = test::TestRequest::get().uri("/health_check").to_request();
        let resp = test::call_service(&mut app, req).await;
        assert!(resp.status().is_success());
        assert_eq!(resp.status().as_u16(), 200);
    }
}