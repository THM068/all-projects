use serde::{Deserialize, Serialize};

#[cfg(test )]
mod test {
    use crate::serde_lesson::Dog;

    #[test]
    fn test_serialize_serde() {
        let dog = Dog {
            name: "Rusty".to_string(),
            age: 8,
        };

        let dog_json = serde_json::to_string_pretty(&dog);
        if(dog_json.is_ok()) {
            println!("dog_json = {:#?}", dog_json.ok().unwrap());
        }
        //assert_eq!(dog_json, r#"{"name":"Rusty","age":8}"#);
    }

    #[test]
    fn test_deserialize_serde() {
        let dog_json = r#"{"name":"Rusty","age":8}"#;
        let dog: Dog = serde_json::from_str(dog_json).unwrap();
        assert_eq!(dog.name, "Rusty");
        assert_eq!(dog.age, 8);
    }
}

#[derive(Debug, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
struct Dog {
    name: String,
    age: u8,
}