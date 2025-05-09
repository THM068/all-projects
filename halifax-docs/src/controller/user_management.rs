use crate::controller::{
    RegisterUserForm, SigninForm, LOGIN, REGISTER_USER, REGISTER_USER_SUCCESS,
};
use crate::entities::prelude::User;
use crate::entities::user;
use crate::repository::user_repository::UserRepository;
use crate::util::hash_password;
use crate::AppConfig;
use bcrypt::verify;
use jsonwebtoken::{decode, encode, DecodingKey, EncodingKey, Header, Validation};
use log::error;
use rocket::form::{Contextual, Form};
use rocket::http::{Cookie, CookieJar, Status};
use rocket::request::{FlashMessage, FromRequest, Outcome};
use rocket::response::{Flash, Redirect};
use rocket::{request, Request, State};
use rocket_dyn_templates::{context, Template};
use sea_orm::{DatabaseConnection, EntityTrait};
use serde::{Deserialize, Serialize};
use std::time::SystemTime;

#[derive(Serialize, Deserialize)]
#[serde(crate = "rocket::serde")]
pub struct Claims {
    sub: i32,
    role: String,
    exp: u64,
}
#[derive(Deserialize)]
#[serde(crate = "rocket::serde")]
pub struct ReqSignIn {
    email: String,
    password: String,
}

#[derive(Serialize, Deserialize)]
#[serde(crate = "rocket::serde")]
pub struct ResSignIn {
    token: String,
}

//route that catches 401 errors
#[catch(401)]
pub fn not_authorised() -> Redirect {
    Redirect::to(uri!("/login"))
}

#[get("/register-user")]
pub async fn show_register_user() -> Template {
    Template::render(
        REGISTER_USER,
        context! {
            title: "Register User"
        },
    )
}
#[get("/register-success")]
pub async fn show_register_success(flash: Option<FlashMessage<'_>>) -> Template {
    let message = flash.map_or_else(|| "User created".into(), |msg| msg.message().to_string());
    Template::render(
        REGISTER_USER_SUCCESS,
        context! {
            message: message
        },
    )
}

#[post("/register-user", data = "<user_form>")]
pub async fn register_user(
    db: &State<DatabaseConnection>,
    user_form: Form<Contextual<'_, RegisterUserForm>>,
) -> Result<Flash<Redirect>, Template> {
    let db = db as &DatabaseConnection;
    let user_form_option = &user_form.value;

    let user_repository = UserRepository::new(db);

    if let Some(ref form) = user_form.value {
        match user_repository.find_one(&form.email).await {
            Ok(user) => {
                if user.is_some() {
                    return Err(Template::render(
                        REGISTER_USER,
                        context! {
                            title: "Register User",
                            errors: vec!["User with email already exists".to_string()]
                        },
                    ));
                }
            }
            Err(err) => {
                error!("Error finding user: {:?}", err);
                return Err(Template::render(
                    REGISTER_USER,
                    context! {
                        title: "Register User",
                        errors: vec!["Error finding user".to_string()]
                    },
                ));
            }
        }

        let id = user_repository.create(form.clone().into()).await;

        match id {
            Ok(id) => {
                let message = Flash::success(
                    Redirect::to(uri!("/register-success")),
                    "User created successfully",
                );
                return Ok(message);
            }
            Err(err) => {
                error!("Error creating user: {:?}", err);
                return Err(Template::render(
                    REGISTER_USER,
                    context! {
                        title: "Register User",
                        errors: vec!["Error creating user".to_string()]
                    },
                ));
            }
        }
    }

    let error_messages: Vec<String> = user_form
        .context
        .errors()
        .map(|error| {
            let name = error.name.as_ref().unwrap().to_string();
            let description = error.to_string();
            format!("'{}' {}", name, description)
        })
        .collect();

    Err(Template::render(
        REGISTER_USER,
        context! {
            title: "Register User",
            errors: error_messages
        },
    ))
}
#[get("/login")]
pub async fn show_login() -> Template {
    Template::render(
        LOGIN,
        context! {
            title: "Sign In"
        },
    )
}
#[post("/sign-in", data = "<req_sign_in>")]
pub async fn sign_in(
    db: &State<DatabaseConnection>,
    req_sign_in: Form<Contextual<'_, SigninForm>>,
    config: &State<AppConfig>,
    cookie_jar: &CookieJar<'_>,
) -> Result<Redirect, Template> {
    let db = db as &DatabaseConnection;
    let config = config as &AppConfig;
    let signin_form = &req_sign_in.value;
    let user_repository = UserRepository::new(db);

    let Some(sign_req_payload) = signin_form else {
        return Err(Template::render(
            REGISTER_USER,
            context! {
                title: "Register User",
                errors: vec!["error: Invalid email or password".to_string()]
            },
        ));
    };

    let user: user::Model = match user_repository.find_one(&sign_req_payload.email).await {
        Ok(user) => {
            let Some(userModel) = user else {
                return Err(Template::render(
                    REGISTER_USER,
                    context! {
                        title: "Register User",
                        errors: vec!["Invalid email or password".to_string()]
                    },
                ));
            };
            userModel
        }
        Err(err) => {
            error!("Error finding user: {:?}", err);
            return Err(Template::render(
                REGISTER_USER,
                context! {
                    title: "Register User",
                    errors: vec!["Error finding user".to_string()]
                },
            ));
        }
    };

    if !verify(&sign_req_payload.password, &user.password).unwrap() {
        return Err(Template::render(
            REGISTER_USER,
            context! {
                title: "Register User",
                errors: vec!["Invalid email or password".to_string()]
            },
        ));
    }

    let claims = Claims {
        sub: user.id,
        role: "user".to_string(),
        exp: SystemTime::now()
            .duration_since(SystemTime::UNIX_EPOCH)
            .unwrap()
            .as_secs()
            + 4 * 60 * 60,
    };

    let token = encode(
        &Header::default(),
        &claims,
        &EncodingKey::from_secret(config.jwt_secret.as_bytes()),
    )
    .unwrap();
    let cookie = Cookie::build(("session_token", token))
        .domain(config.cookie_domain.clone())
        .max_age(time::Duration::days(30)) // Cookie is valid for 1 day
        .build();
    cookie_jar.add_private(cookie);
    Ok(Redirect::to(uri!("/")))
}

pub struct AuthenticatedUser {
    pub id: i32,
}

#[derive(Debug)]
pub enum LoginError {
    InvalidData,
    InvalidToken,
    UsernameDoesNotExist,
    WrongPassword,
}

#[rocket::async_trait]
impl<'r> FromRequest<'r> for AuthenticatedUser {
    type Error = LoginError;

    async fn from_request(request: &'r Request<'_>) -> Outcome<Self, Self::Error> {
        let cookie_jar = request.cookies();
        let session_token = cookie_jar.get_private("session_token");
        if let Some(cookie) = session_token {
            let config = request.rocket().state::<AppConfig>().unwrap();
            let data = decode::<Claims>(
                cookie.value(),
                &DecodingKey::from_secret(config.jwt_secret.as_bytes()),
                &Validation::new(jsonwebtoken::Algorithm::HS256),
            );

            let claims = match data {
                Ok(p) => p.claims,
                Err(_) => return Outcome::Error((Status::Unauthorized, LoginError::InvalidToken)),
            };
            return Outcome::Success(AuthenticatedUser { id: claims.sub });
        } else {
            println!("No cookie");
            return Outcome::Error((Status::Unauthorized, LoginError::InvalidData));
        }
    }
}
