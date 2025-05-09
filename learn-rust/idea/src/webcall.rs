use std::collections::HashMap;
use serde::{Deserialize, Serialize};

const API_BASE_URL: &str = "http://localhost:9091";

#[derive(Debug, Serialize, Deserialize)]
pub struct IdeaWebResponse {
    pub id: u32,
    pub title: String,
    pub solved: bool
}

pub fn list() -> Result<Vec<IdeaWebResponse>, reqwest::Error> {
    let url = format!("{API_BASE_URL}/idea");
    let resp: Vec<IdeaWebResponse> = reqwest::blocking::get(url)?
        .json()?;
    Ok(resp)
}

pub fn create(title: &str) -> Result<IdeaWebResponse, reqwest::Error> {
    let mut payload = HashMap::new();
    payload.insert("title", title);
    let url = format!("{API_BASE_URL}/idea");
    let resp: IdeaWebResponse = reqwest::blocking::Client::new()
        .post(url)
        .json(&payload)
        .send()?
        .json()?;
    Ok(resp)
}

pub fn remove(id: i32) -> Result<(), reqwest::Error> {
    let url = format!("{API_BASE_URL}/idea/{id}");
    reqwest::blocking::Client::new()
        .delete(url)
        .send()?;
    Ok(())
}

pub fn solve(id: i32) -> Result<IdeaWebResponse, reqwest::Error> {
    let url = format!("{API_BASE_URL}/idea/{id}");
    let resp: IdeaWebResponse = reqwest::blocking::Client::new()
        .put(url)
        .send()?
        .json()?;
    Ok(resp)
}