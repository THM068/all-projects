use rocket_dyn_templates::{context, Template};
use crate::controller::CHURCH_MINUTES;

#[get("/church-minutes")]
pub async fn get_minutes() -> Template {
    Template::render(CHURCH_MINUTES,
                     context! {
        title: "Church Minutes"
    })
}