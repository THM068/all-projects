#![allow(unused)]

use std::collections::hash_map::Keys;
use tokio_stream::StreamExt;
use aws_sdk_s3::{config, Client};
use aws_sdk_s3::config::Credentials;
use aws_config::{BehaviorVersion, credential_process, Region};
use anyhow::{anyhow, bail, Context, Result};
use std::env;
use std::io::BufRead;

const REGION: &str = "eu-west-2";
const ENV_CRED_KEY_ID: &str = "S3_KEY_ID";
const ENV_CRED_KEY_SECRET: &str = "S3_KEY_SECRET";

const BUCKET_NAME: &str = "appraisaldb";
#[tokio::main]
async fn main() -> Result<()> {
    println!("Hello, world!");

    let client = get_aws_client(REGION)?;

    let keys = list_keys(&client, BUCKET_NAME).await?;

    println!("{}", keys.join("\n"));
    Ok(())
}

async fn list_keys(client: &Client, bucket: &str) -> Result<Vec<String>> {
    //build aws request
    let request = client.list_objects().bucket(bucket);

    //Execute
    let result = request.send().await?;

    //collect
    let keys = result.contents.unwrap_or_default();
    let keys =  keys.iter()
        .filter_map(|o| o.key.as_ref())
        .map(|s| s.to_string())
        .collect::<Vec<_>>();

    Ok(keys)
}

fn get_aws_client(region: &str) -> Result<Client> {
    let key_id = env::var(ENV_CRED_KEY_ID).context("Missing S3_KEY_ID")?;
    let key_secret = env::var(ENV_CRED_KEY_SECRET).context("Missing S3_KEY_SECRET")?;

    let cred = Credentials::new(key_id, key_secret, None, None, "loaded-from-custom-env");

    // build the aws client
    let region = Region::new(region.to_string());
    let conf_builder = config::Builder::new().region(region).credentials_provider(cred);
    let conf = conf_builder.behavior_version(BehaviorVersion::latest()).build();

    // build aws client
    let client = Client::from_conf(conf);
    Ok(client)
}
