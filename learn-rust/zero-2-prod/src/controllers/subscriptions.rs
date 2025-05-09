use actix_web::{HttpResponse, post, Responder, web, get};
use serde::{Deserialize, Serialize};
use uuid::Uuid;
use crate::models::{Subscriber, SubscriberResponse};
use crate::SubscriberDb;

#[get("/subscribe/{id}")]
pub async fn get_subscriptions(id: web::Path<String>, subscriber_db: web::Data<SubscriberDb>) -> impl Responder {
    let db = subscriber_db.lock().unwrap();
    let request_id = Uuid::new_v4();
    match db.get(&id.to_string()) {
        Some(subscriber) => {
            tracing::info!("Subscriber found with request_id {} and id: {}", request_id, id.to_string());
            HttpResponse::Ok().json(SubscriberResponse{
                id: subscriber.id.to_owned(),
                name: subscriber.name.to_owned(),
                email: subscriber.email.to_owned(),
            })
        },
        None => {
            tracing::info!("Subscriber not found with id: {} and request_id {}", id.to_string(), request_id);
            HttpResponse::NotFound().body("Subscriber not found")
        }

    }
}
#[post("/subscribe")]
pub async fn subscribe(form: web::Form<FormData>, subscriber_db: web::Data<SubscriberDb>) -> impl Responder {
    let form = form.into_inner();
    let mut db = subscriber_db.lock().unwrap();
    let id: String = Uuid::new_v4().to_string();

    db.insert(id.to_owned(), Subscriber{
        id: id.to_owned(),
        name: form.name.to_owned(),
        email: form.email.to_owned(),
        date_created: chrono::Utc::now(),
    });
    HttpResponse::Created().json( SubscriberResponse{
        id: id.to_owned(),
        name: form.name.to_owned(),
        email: form.email.to_owned(),
    })
}
#[cfg(test)]
mod tests {
    use super::*;
    use actix_web::{test, App};

    #[tokio::test]
    async fn test_health_check() {
        let test_cases = vec![
            ("name=le%20guin", "missing the email"),
            ("email=ursula_le_guin%40gmail.com", "missing the name"),
            ("", "missing both name and email")
        ];
        let mut app = test::init_service(App::new().service(subscribe)).await;

        for (body, error_message) in test_cases {
            let req = test::TestRequest::post()
                .uri("/subscribe")
                .set_payload(body)
                .to_request();
            let response = test::call_service(&mut app, req).await;

            assert_eq!(400, response.status().as_u16(),
                "The API did not fail with 400 Bad Request when the payload was {}.", error_message
            )
        }

    }
}
#[derive(Serialize, Deserialize)]
struct FormData {
    name: String,
    email: String,
}