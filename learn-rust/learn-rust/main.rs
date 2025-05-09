
use mini_redis::{client, Result};
#[allow(unused_imports)]
#[tokio::main]
async fn main() -> Result<()> {
    // Open a connection to the mini-redis address.
    let mut client = client::connect("127.0.0.1:6379").await?;

    println!("hello rust");

    Ok(())
}