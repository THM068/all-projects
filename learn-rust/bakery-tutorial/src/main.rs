use futures::executor::block_on;
use sea_orm::{ActiveModelTrait, ActiveValue, ColumnTrait, Database, DbErr, EntityTrait, QueryFilter};
use sea_orm::ActiveValue::Set;

use entities::{*, prelude::*};

mod entities;
const DATABASE_URL: &str = "mysql://root:@localhost:3306";

const DB_NAME: &str = "bakeries_store";
fn main() {
    if let Err(err) = block_on(run()) {
        panic!("{}", err);
    }

    println!("Hello, world!");
}

async fn run() -> Result<(), DbErr> {
    let url = format!("{}/{}", DATABASE_URL, DB_NAME);
    let db = Database::connect(url).await?;

    let happy_bakery = bakery::ActiveModel {
        name: Set("Happy Bakery".to_owned()),
        profit_margin: Set(0.1),
        ..Default::default()
    };
    let res = Bakery::insert(happy_bakery).exec(&db).await?;

    let sad_bakery = bakery::ActiveModel {
        id: Set(res.last_insert_id),
        name: Set("Sad Bakery".to_owned()),
        profit_margin: ActiveValue::NotSet,
        ..Default::default()
    };

    Bakery::update(sad_bakery).exec(&db).await?;


    let john = chef::ActiveModel {
        name: Set("John".to_owned()),
        bakery_id: Set(res.last_insert_id),
        ..Default::default()
    };

    let john_res = Chef::insert(john).exec(&db).await?;

    let bakeries: Vec<bakery::Model> = Bakery::find().all(&db).await?;
    assert_eq!(bakeries.len(), 1);

    for bakery in bakeries.iter() {
        println!("Bakery: {:?}", bakery);
    }

    // Finding by id is built-in
    let sad_bakery: Option<bakery::Model> = Bakery::find_by_id(res.last_insert_id).one(&db).await?;
    assert_eq!(sad_bakery.unwrap().name, "Sad Bakery");

    let sad_bakery: Option<bakery::Model> = Bakery::find()
        .filter(bakery::Column::Name.eq("Sad Bakery"))
        .one(&db)
        .await?;
    println!("Sad Bakery: {:?}", sad_bakery);

    let john = chef::ActiveModel {
        id: Set(john_res.last_insert_id),
        ..Default::default()
    };

    let x = john.delete(&db).await?;

    let sad_bakery = bakery::ActiveModel {
        id: Set(res.last_insert_id), // The primary key must be set
        ..Default::default()
    };
    sad_bakery.delete(&db).await?;

    let bakeries: Vec<bakery::Model> = Bakery::find().all(&db).await?;
    assert!(bakeries.is_empty());

    let la_boulangerie = bakery::ActiveModel {
        name: Set("La boulangerie".to_owned()),
        profit_margin: Set(0.5),
        ..Default::default()
    };

    let la_boulangerie_res = Bakery::insert(la_boulangerie).exec(&db).await?;

    for chef_name in ["Jolie", "Charles", "Madeleine", "Frederic"] {
        let chef = chef::ActiveModel {
            name: ActiveValue::Set(chef_name.to_owned()),
            bakery_id: ActiveValue::Set(la_boulangerie_res.last_insert_id),
            ..Default::default()
        };
        Chef::insert(chef).exec(&db).await?;
    }

    print!("All done ...");

    Ok(())
}
