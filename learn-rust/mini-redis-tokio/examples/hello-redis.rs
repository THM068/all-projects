use mini_redis::{client, Result};

async fn hello_world() {
    println!(", world");
}

#[tokio::main]
async fn main() -> Result<()> {
    println!("Hello, world!");

    let op = hello_world();

    print!("Hello");

    op.await;

    let mut client = client::connect("127.0.0.1:6380").await?;

    client.set("hello", "world".into()).await?;
    client.set("kamala", "harris".into()).await?;

    let result = client.get("hello").await?;

    println!("got value from the server; result={:?}", result);

    Ok(())
}
