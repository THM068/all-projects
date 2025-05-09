use tokio::net::{TcpListener, TcpStream};
use tokio::io::{AsyncReadExt, AsyncWriteExt};
use tokio::time::timeout;
use mini_redis::{client, Result};

#[tokio::main]
async fn main() -> Result<()> {
    let mut client = client::connect("127.0.0.1:6380").await?;

    // Set the key "hello" with value "world"
    client.set("hello", "world".into()).await?;

    // Get key "hello"
    let result = client.get("hello").await?;

    println!("got value from the server; result={:?}", result);

    say_world().await;

    Ok(())
}

async fn say_world() {
    println!("world");
}
enum Input {
    Empty,
    Question(String),
}

async fn get_user_input(socket: &mut TcpStream) -> Result<Input> {
    let mut buffer = [0; 1024];
    match timeout(std::time::Duration::from_secs(30), socket.read(&mut buffer)).await? {
        Ok(usize)  => {

        },
        Err(e) => {}
    }
    Ok(Input::Empty)
}
