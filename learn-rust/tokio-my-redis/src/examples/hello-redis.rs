use mini_redis::{client, Result};
#[allow(unused_imports)]
#[tokio::main]
async fn main() -> Result<()>{
    // Open a connection to the mini-redis address.
    let mut client = client::connect("127.0.0.1:6379").await?;

    client.set("hello", "world".into()).await?;

    // Get key "hello"
    let result = client.get("hello").await?;

    println!("got value from the server; result={:?}", result);
    println!("Hello, world!");

    // Calling `say_world()` does not execute the body of `say_world()`.
    let op = say_world();

    println!("hello");

    // Calling `.await` on `op` starts executing `say_world`.
    op.await;



    Ok(())
}

async fn say_world() {
    print!(" world")
}
