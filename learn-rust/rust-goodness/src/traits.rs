#[cfg(test)]
mod test {
    use crate::traits::{EvenNumber, Person, Person_Dto, YouTubeVideo};

    #[test]
    fn test_youtube_video() {
        let video = YouTubeVideo::new("Rust", "A video about Rust");
        assert_eq!(video.title, "Rust");
        assert_eq!(video.description, "A video about Rust");
    }

    #[test]
    fn test_person_dto() {
        let person_dto = Person_Dto::new("John", 30);
        let person: Person = person_dto.into();

        assert_eq!(person.name, "John");
        assert_eq!(person.age, 30);

        let result: Result<EvenNumber, ()> = 8i32.try_into();
        assert_eq!(result, Ok(EvenNumber(8)));
        let result: Result<EvenNumber, ()> = 5i32.try_into();
        assert_eq!(result, Err(()));

    }


}

struct YouTubeVideo {
    title: String,
    description: String,
}

impl YouTubeVideo {
    fn new<T>(title: T, description: T) -> Self
    where
        T: Into<String>,
    {
        Self {
            title: title.into(),
            description: description.into(),
        }
    }
}

struct Person_Dto {
    name: String,
    age: u32,
}

impl Person_Dto {

    fn new<T>(name: T, age: u32) -> Self
    where
        T: Into<String>,
    {
        Self {
            name: name.into(),
            age: age
        }
    }
}

struct Person {
    name: String,
    age: u32,
}

impl From<Person_Dto> for Person {
    fn from(person_dto: Person_Dto) -> Self {
        Self {
            name: person_dto.name,
            age: person_dto.age,
        }
    }
}

#[derive(Debug, PartialEq)]
struct EvenNumber(i32);

impl TryFrom<i32> for EvenNumber {
    type Error = ();

    fn try_from(value: i32) -> Result<Self, Self::Error> {
        if value % 2 == 0 {
            Ok(Self(value))
        } else {
            Err(())
        }
    }
}

