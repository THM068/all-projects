use config::ClientConfig;
use rdkafka::{config, Message};
use rdkafka::consumer::{CommitMode, Consumer, StreamConsumer};
use crate::request_test::getTodos;

mod zip_combine_iterator;
mod logs_errors;
mod iterators;
mod generics;
mod basket;
mod request_test;
mod extension_trait;
mod closures;
mod serde_lesson;
mod smart_pointers;
mod idioms;
mod traits;
mod collections;
mod design_patterns;

#[tokio::main]
async fn main() -> Result<(), Box<dyn std::error::Error>> {
    start().await;
    Ok(())
}

fn create() -> StreamConsumer {
    let binding = ClientConfig::new();
    let mut config = binding;
    let consumer = config
        .set("group.id", "test-group")
        .set("bootstrap.servers", "localhost:19092")
        .set("auto.offset.reset", "earliest")
        .set("socket.timeout.ms", "60000")
        .create().expect("Consumer creation failed");

    consumer
}
async fn start() {
    let consumer: StreamConsumer = create();
    consume(consumer).await;
}

async fn consume(consumer: StreamConsumer) {
    consumer.subscribe(&["signup-events"])
        .expect("Can't subscribe to specified topic");

    loop {
        match consumer.recv().await {
            Err(error) => {
                eprintln!("Error while consuming message: {:?}", error);
            }
            Ok(message) => {
                match message.payload_view::<str>() {
                    None => {
                        eprintln!("No payload found");
                    }
                    Some(Ok(payload)) => {
                        println!("Consumed message: {}", payload);
                    }
                    Some(Err(error)) => {
                        eprintln!("Error while deserializing message payload: {:?}", error);
                    }
                }
                consumer.commit_message(&message, CommitMode::Async).unwrap();
            }
        }
    }
}

