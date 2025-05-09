use serde_json::{json, Value};

const place_holder_url: &str = "https://jsonplaceholder.typicode.com/posts/1";
fn main() -> Result<(), reqwest::Error> {
    println!("Hello, world!");

    let response: serde_json::Value =
        reqwest::blocking::get(place_holder_url)?
            .json()?;


    println!("Title: {}", response["title"]);

    Ok(())
}
