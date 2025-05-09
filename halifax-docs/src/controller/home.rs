use crate::controller::user_management::AuthenticatedUser;
use rocket::response::Redirect;
use rocket_dyn_templates::{context, Template};

#[get("/")]
pub fn index(authenticated_user: AuthenticatedUser) -> Redirect {
    Redirect::to(uri!("/", hello(name = "your-name")))
}
#[get("/hello/<name>")]
pub fn hello(name: &str, authenticated_user: AuthenticatedUser) -> Template {
    Template::render(
        "index",
        context! {
            title: "Hello",
            name: Some(name),
            items: vec!["Example", "List", "Of", "Five", "Items"],
        },
    )
}
