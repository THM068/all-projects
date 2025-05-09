use mini_redis::{client, Result};
#[allow(unused_imports)]
#[tokio::main]
async fn main() -> Result<()>{
    // Open a connection to the mini-redis address.
    let mut client = client::connect("127.0.0.1:6379").await?;

    client.set("hello", "world".into()).await?;
    let name = String::from("hello world");

    let task = tokio::spawn(async move {
        println!("Hello, rust! {}", name);
    });

    task.await.unwrap();

    match Coin::DIME {
        Coin::PENNY => {
            println!("1 cent")
        }
        Coin::NICKEL => {
            println!("5 cent")
        }
        Coin::DIME => {
            println!("10 cent")
        }
        Coin::QUARTER => {
            println!("25 cent")
        }
    }

    let mut stack: Vec<u8> = Vec::new();
    stack.push(1);
    stack.push(2);
    stack.push(3);
    stack.push(4);
    println!("{:?}", stack);
    while let Some(top) = stack.pop() {
        println!("{}", top);
    }

    process_greet(greet, "Thando process_greet");

    let  names: Vec<&str> = vec!["Thando", "Mafela", "Mlauzi"];

    for (index, name) in names.iter().enumerate() {
        println!("{}  {}", index, name)
    }







    Ok(())
}

enum Coin {
    PENNY,
    NICKEL,
    DIME,
    QUARTER
}

fn greet(name: &str){
    println!("{}", name);
}

fn process_greet(f: fn(&str), name: &str) {
    f(name)
}
