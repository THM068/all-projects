
// use std::env;
use anyhow::{ Result};
use reqwest::{Response, StatusCode};
use serde::Serialize;
use serde::Deserialize;


const place_holder_url: &str = "https://jsonplaceholder.typicode.com/posts/1";
#[derive(Debug, Serialize, Deserialize)]
struct Post {
    userId: i32,
    id: i32,
    title: String,
    body: String,
}

#[tokio::main]
async fn main() -> Result<(), reqwest::Error> {
    let url = "https://jsonplaceholder.typicode.com/posts/1";
    let urlList = "https://jsonplaceholder.typicode.com/posts";
    let client = reqwest::Client::new();

    let response: Response = client.get(url).send().await.unwrap();
    println!("{:?}", response);
    match response.status() {
        reqwest::StatusCode::OK => {
            let post: Post = response.json().await.unwrap();

            println!("Post with ID {}:", post.id);
            println!("Title: {}", post.title);
            println!("Body: {}", post.body);
        }
        _ => {
            println!("Error: {:?}", response.status());
        }
    }

    let response: Response = client.get(urlList).send().await.unwrap();
    match response.status() {
        StatusCode::OK => {
            let post: Vec<Post> = response.json().await.unwrap();

            for p in post.iter() {
                println!("Post with ID {}:", p.id);
                println!("Title: {}", p.title);
                println!("Body: {}", p.body);
                println!("\n");
            }
        }
        _ => {
            println!("Error: {:?}", response.status());
        }
    }

    let is_even = |x| x % 2 == 0;

    let is_odd = |x| -> bool {
        x % 2 != 0
    };
    println!("is 3 even {}", is_even(3));
    println!("is 3 odd {}", is_odd(3));
    Ok(())
}


