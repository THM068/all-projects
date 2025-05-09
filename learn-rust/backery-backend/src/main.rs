use futures::executor::block_on;
use sea_orm::{ActiveValue, ConnectionTrait, Database, DbBackend, DbErr, EntityTrait, Statement};

mod entities;
use entities::{prelude::*, *};

const DATABASE_URL: &str = "mysql://root:@localhost:3306";
const DB_NAME: &str = "bakeries_db";

fn main() {
    println!("Hello, world!");

    if let Err(err) = block_on(run()) {
        panic!("{}", err);
    }
}

async fn run() -> Result<(), DbErr> {
    let db = Database::connect(DATABASE_URL).await?;

    let db = &match db.get_database_backend() {
        DbBackend::MySql => {
            db.execute(Statement::from_string(
                db.get_database_backend(),
                format!("CREATE DATABASE IF NOT EXISTS `{}`;", DB_NAME),
            )).await?;

            let url = format!("{}/{}", DATABASE_URL, DB_NAME);
            Database::connect(&url).await?
        }
        _ => db,
    };


    let happy_bakery = bakery::ActiveModel {
        name: ActiveValue::Set("Happy Bakery".to_owned()),
        profit_margin: ActiveValue::Set(0.0),
        ..Default::default()
    };
    let res = Bakery::insert(happy_bakery).exec(db).await?;

    let bakers = vec!["Alice", "Bob", "Charlie", "David", "Eve"];

    // for bakery in bakers {
    //     let baker = bakery::ActiveModel {
    //         name: ActiveValue::Set(bakery.to_owned()),
    //         profit_margin: ActiveValue::Set(0.0),
    //         ..Default::default()
    //     };
    //     Bakery::insert(baker).exec(db).await?;
    // }

    let bakeries = Bakery::find().all(db).await?;
    println!("{:?}", bakeries);

    // let la_boulangerie = bakery::ActiveModel {
    //     name: ActiveValue::Set("La Boulangerie".to_owned()),
    //     profit_margin: ActiveValue::Set(0.0),
    //     ..Default::default()
    // };
    //
    // let bakery_res = Bakery::insert(la_boulangerie).exec(db).await?;
    //
    // for chef_name in ["Jolie", "Charles", "Madeleine", "Frederic"] {
    //     let chef = chef::ActiveModel {
    //         name: ActiveValue::Set(chef_name.to_owned()),
    //         bakery_id: ActiveValue::Set(bakery_res.last_insert_id),
    //         ..Default::default()
    //     };
    //     Chef::insert(chef).exec(db).await?;
    // }
    Ok(())
}
