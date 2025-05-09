use crate::util::hash_password;
use bcrypt::{hash, DEFAULT_COST};
use sea_orm::ActiveValue::Set;

pub mod home;
pub mod user_management;
pub mod minutes;
pub(crate) mod members;

#[derive(FromForm, Debug, PartialEq, Clone)]
pub struct SigninForm {
    #[field(validate=len(1..))]
    pub email: String,
    #[field(validate=len(1..))]
    pub password: String,
}

#[derive(FromForm, Debug, PartialEq, Clone)]
pub struct RegisterUserForm {
    #[field(validate=len(1..))]
    pub email: String,
    #[field(validate=len(1..))]
    pub password: String,
}

impl From<RegisterUserForm> for crate::entities::user::ActiveModel {
    fn from(form: RegisterUserForm) -> Self {
        Self {
            email: Set(form.email),
            password: Set(hash_password(form.password.as_str())),
            enabled: Set(Some(true)),
            account_locked: Set(Some(false)),
            ..Default::default()
        }
    }
}

//Views
const REGISTER_USER: &str = "register_user";
const LOGIN: &str = "login";
const REGISTER_USER_SUCCESS: &str = "register_success";
const CHURCH_MINUTES: &str = "church_minutes";
const CHURCH_MEMBERS: &str = "church_members";


