use serde::{Deserialize, Serialize};
use futures::executor::block_on;
use colored::Colorize;
#[cfg(test)]
mod test {
    use futures::executor::block_on;
    use crate::request_test::{add_todo, getTodos, delete_todo};

    #[tokio::test]
    async fn test_get_todo() {
        let _ = getTodos().await;
    }

    #[tokio::test]
    async fn test_add_todo() {
        let _ = add_todo().await;
    }

    #[tokio::test]
    async fn test_delete_todo() {
        let todo = delete_todo(1).await;
        println!("{:?}", todo);
        // assert_eq!(todo.id, 1);
        // assert_eq!(todo.completed, false);
        // assert_eq!(todo.title, "delectus aut autem");
    }

}

pub async fn getTodos() -> Result<(), Box<dyn std::error::Error>> {
    let client = reqwest::Client::new();
    let body = client.get("https://jsonplaceholder.typicode.com/todos/1")
        .send()
        .await?
        .json::<Todo>()
        .await?;
    println!("{} {:?}","body =".bold().red(),body);
    Ok(())
}

pub async fn add_todo() -> Result<(), Box<dyn std::error::Error>> {
    let client = reqwest::Client::new();
    let body = client.post("https://jsonplaceholder.typicode.com/todos")
        .json(&Todo {
            userId: 10,
            id: 210,
            title: "delectus aut autem".to_string(),
            completed: false,
        })
        .send()
        .await?
        .json::<Todo>()
        .await?;
    println!("body = {body:?}");
    Ok(())
}

pub async fn delete_todo(id: u32) -> Result<(), Box<dyn std::error::Error>> {
    let client = reqwest::Client::new();
    let body = client.delete(&format!("https://jsonplaceholder.typicode.com/todos/{}", id))
        .send()
        .await?;
    if body.status().is_success() {
        return Ok(());
    } else {
        return Err("Failed to delete todo".into());
    }
}

#[derive(Debug, Serialize, Deserialize)]
pub struct Todo {
    userId: i32,
    id: i32,
    title: String,
    completed: bool,
}