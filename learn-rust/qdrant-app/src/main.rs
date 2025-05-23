use anyhow::Result;
use qdrant_client::prelude::*;
use qdrant_client::qdrant::vectors_config::Config;
use qdrant_client::qdrant::{
    Condition, CreateCollection, Filter, SearchPoints, VectorParams, VectorsConfig,
};
use serde_json::json;
use serde::{Deserialize, Serialize};

#[derive(Serialize, Deserialize, Debug)]
struct User {
    id: u32,
    name: String,
    email: String,
}

#[tokio::main]
async fn main() -> Result<()> {
    println!("Hello, world!");

    let people = json!([
        {
            "id": 1,
            "name": "Alice",
            "email": "alice@example.com"
        },
        {
            "id": 2,
            "name": "Bob",
            "email": "bob@example.com"
        }
    ]);

    println!("{}", people.to_string());




    let client = QdrantClient::from_url("http://localhost:6334").build()?;

    let collection_list = client.list_collections().await?;
    dbg!(collection_list);

    let collection_name = "test";
    client.delete_collection(collection_name).await?;

    client
        .create_collection(&CreateCollection {
            collection_name: collection_name.into(),
            vectors_config: Some(VectorsConfig {
                config: Some(Config::Params(VectorParams {
                    size: 10,
                    distance: Distance::Cosine.into(),
                    ..Default::default()
                })),
            }),
            ..Default::default()
        })
        .await?;

    let collection_info = client.collection_info(collection_name).await?;
    println!("******* Collection Info *******");
    dbg!(collection_info);

    let payload: Payload = json!(
       [ {
            "foo": "Bar",
            "bar": 12,
            "baz": {
                "qux": "quux"
            }
        }]
    )
        .try_into()
        .unwrap();

    let points = vec![PointStruct::new(0, vec![12.; 10], payload)];
    client.upsert_points_blocking(collection_name, None, points, None).await?;

    let search_result = client
        .search_points(&SearchPoints {
            collection_name: collection_name.into(),
            vector: vec![11.; 10],
            filter: Some(Filter::all([Condition::matches("bar", 12)])),
            limit: 10,
            with_payload: Some(true.into()),
            ..Default::default()
        })
        .await?;
    dbg!(&search_result);

    let found_point = search_result.result.into_iter().next().unwrap();
    let mut payload = found_point.payload;
    let baz_payload = payload.remove("baz").unwrap().into_json();
    println!("baz: {}", baz_payload);
    // baz: {"qux":"quux"}

    Ok(())
}
