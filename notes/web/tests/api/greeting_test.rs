use googletest::prelude::*;
use notes_macros::test;
use notes_web::controllers::greeting::Greeting;
use notes_web::test_helpers::{BodyExt, RouterExt, TestContext};

#[test]
async fn test_hello(context: &TestContext) {
    let response = context.app.request("/greet").send().await;

    let greeting: Greeting = response.into_body().into_json().await;
    assert_that!(greeting.hello, eq(&String::from("world za`")));
}
