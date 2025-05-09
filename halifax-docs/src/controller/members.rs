use rocket::State;
use rocket_dyn_templates::{context, Template};
use sea_orm::{DatabaseConnection, DbConn, EntityTrait};
use crate::controller::{CHURCH_MEMBERS, CHURCH_MINUTES};
use crate::entities::member::Model;
use crate::entities::prelude::Member;

#[get("/members")]
pub async fn get_members(db: &State<DatabaseConnection>) -> Template {
    let db = db as &DatabaseConnection;
    let membersResult = Member::find().all(db).await;

    // let Ok(members) = membersResult else {
    //     println!("Error getting members: {:?}", membersResult);
    // };
    //

    let members = vec!["John Doe", "Jane Doe", "John Smith", "Jane Smith"];
    Template::render(CHURCH_MEMBERS,
                     context! {
        title: "Church Members",
        members: members

    })
}