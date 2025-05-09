
fn main() {
    println!("Hello, world!");

    let person = Person::new("Thando".to_string(), "Mafela".to_string());
    assert_eq!("Thando".to_string(), person.name);
    assert_eq!("Mafela".to_string(), person.surname);

    let person = Person::default();

    println!("{:?}", person);

    let person = Person {
        name: "Thomas".to_string(),
        ..Default::default()
         
    };
    println!("{:?}", person);

    let s = String::from("Hello, world!");
    process(s); // Ownership of the string in `s` moved into `process`

}

#[derive(Default, Debug)]
struct Person {
    name: String,
    surname: String
}

impl Person {
    
    pub fn new(name: String, surname: String) -> Self {
        Self {
            name: name,
            surname: surname
        }
    }
}

fn process(input: String) {}

fn longest_string<'a>(x: &'a String, y: &'a String) -> &'a String {
    if x.len() > y.len() {
        x
    }
    else {
        y
    }
}

#[derive(Debug)]
struct Highlight<'document>(&'document str);
