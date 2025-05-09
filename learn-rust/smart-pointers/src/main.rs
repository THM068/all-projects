
use std::rc::Rc;
use std::sync::{Arc, Mutex};
use crate::List::{Cons, Nil};
use std::collections::HashMap;

#[derive(Debug)]
struct Contact {
    name: String,
    email: String,
    phone: String,
}

type Contacts = Arc<Mutex<HashMap<String, Contact>>>;

fn closure<F: FnOnce() + 'static>(f: F) {
    f();
}

fn do_something<F>(f: F) where F: FnOnce(){
    f();
}
fn update_contact(contacts: &Contacts, name: &str, new_email: &str, new_phone: &str) {
    let mut map = contacts.lock().unwrap();
    if let Some(contact) = map.get_mut(name) {
        contact.email = new_email.to_string();
        contact.phone = new_phone.to_string();
    }
}
fn main() {
    let mut contacts: Contacts = Arc::new(Mutex::new(HashMap::new()));

    {
        let mut map = contacts.lock().unwrap();
        map.insert(
            "alice".to_string(),
            Contact {
                name: "Alice".to_string(),
                email: "alice@example.com".to_string(),
                phone: "123-456-7890".to_string(),
            },
        );
        map.insert(
            "bob".to_string(),
            Contact {
                name: "Bob".to_string(),
                email: "bob@example.com".to_string(),
                phone: "098-765-4321".to_string(),
            },
        );
    }

    // Update a contact
    update_contact(&contacts, "alice", "alice_new@example.com", "111-222-3333");

    // Verify the update
    let map = contacts.lock().unwrap();
    if let Some(contact) = map.get("alice") {
        println!("{:?}", contact);
    }


    let a = Rc::new(Cons(5, Rc::new(Cons(10, Rc::new(Nil)))));
    let b = Cons(3, Rc::clone(&a));
    let c = Cons(4, Rc::clone(&a));

    println!("a: {:?}", Rc::strong_count(&a));
    let b = Box::new(5);
    println!("b: {}", b);

    do_something(|| println!("Hello, world"));
    do_something(|| println!("Hello, world"));
    let m = 5;
    let add = |a: i32, b: i32| a + b + m;
    let result = add(1, 2);
    println!("Result: {}", result);
    let mut ages = vec![1, 2, 3, 4, 5];

    closure(move|| {
        ages.push(6);
    });
    let a = 123_321;
    let b = Box::new(123_321);

    let r = a + *b;

    println!("r: {}", r);

    let x = Holder {
        next_holder: Some(Box::new(Holder {
            next_holder: Some(Box::new(Holder { next_holder: None })),
        })),
    };

    println!("{x:?}");

    let calgary = City {
        name: "Calgary".to_string(),
        population: 1_200_000,
        // Pretend that this string is very very long
        city_history: Rc::new("Calgary began as a fort called Fort Calgary that...".to_string()),
    };

    let canada_cities = CityData {
        names: vec![calgary.name], // This is using calgary.name, which is short
        histories: vec![calgary.city_history.clone()], // But this String is very long
    };

    println!("Calgary's history is: {}", calgary.city_history);
    println!("{}", Rc::strong_count(&calgary.city_history));

    let s: Rc<String> = Rc::new("hello".to_string());
    let t: Rc<String> = s.clone();
    let u: Rc<String> = s.clone();

    println!("count {}", Rc::strong_count(&s));

    let hello = String::from("hello");
    let world = String::from("worldi");
    let result = find_longest(&hello, &world);
    println!("{result}");

    let name = String::from("Thomas Mafela");
    let person = Person::new(&name);
    person.greet();
    println!("----------------------------------");

    let author_name = "Maya Angelou";
    let author = Author::new(&author_name);
    let book_title = "I Know Why the Caged Bird Sings";

    let book = Book::new(&book_title, author, 1969);
    book.display();
    let author_name2 = "Chimamanda Ngozi Adichie";
    let author2 = Author::new(&author_name2);
    let book_title2 = "Americanah";
    let book2 = Book::new(&book_title2, author2, 2013);
    book2.display();

    println!("------------Mutex----------------------");
    let my_mutex = Mutex::new(5);

     *my_mutex.lock().unwrap() = 7;

    println!("{:?}", my_mutex);

    let counter = Arc::new(Mutex::new(0));
    let mut handles = vec![];

    for _ in 0..20 {
        let counter = Arc::clone(&counter);
        let handle = std::thread::spawn(move || {
            let mut num = counter.lock().unwrap();

            *num += 1;
        });
        handles.push(handle);
    }
    for handle in handles {
        handle.join().unwrap();
    }
    println!("Result: {}", *counter.lock().unwrap());

    let data = Rc::new(Secret(1234));
    let data_clone = Rc::clone(&data);

    println!("Original: {:?}", data);
    println!("Clone: {:?}", data_clone);

    let node1 = Rc::new(Node { value: 1, next: None });
    let node2 = Rc::new(Node { value: 2, next:
    Some(Rc::clone(&node1)) });
    let node3 = Rc::new(Node { value: 3, next:
    Some(Rc::clone(&node2)) });
    println!("Node 1: {:?}", node1);
    println!("Node 2: {:?}", node2);
    println!("Node 3: {:?}", node3);

    let car: Box<dyn Vehicle>;

    car = Box::new(Car);

    car.drive();

}

#[derive(Debug)]
struct Node {
    value: i32,
    next: Option<Rc<Node>>,
}
#[derive(Debug)]
struct Secret(u32);
struct Person<'a> {
    name: &'a str
}

impl <'a> Person<'a> {
    fn new(name: &'a str) -> Person<'a> {
        Person { name }
    }

    fn greet(&self) {
        println!("Hello, my name is {}", self.name);
    }
}

struct Author<'a> {
    name: &'a str,
}
struct Book<'a> {
    title: & 'a str,
    author: Author<'a>,
    publication_year: i32
}

impl <'a> Author<'a> {
    fn new(name: &'a str) -> Author<'a> {
        Author { name }
    }

    fn display(&self) {
        println!("Author: {}", self.name);
    }
}

impl <'a> Book<'a> {
    fn new(title: &'a str, author: Author<'a>, publication_year: i32) -> Book<'a> {
        Book { title, author, publication_year }
    }

    fn display(&self) {
        println!("Title: {}", self.title);
        self.author.display();
        println!("Year: {}", self.publication_year);
    }
}
fn find_longest<'a>(t1: & 'a str, t2: &'a str) -> & 'a str {
   use std::cmp::Ordering::*;

    match t1.len().cmp(&t2.len()) {
    Greater => &t1,
    Equal => "",
    Less => &t2,
    }

}

#[derive(Debug)]
struct Holder {
    next_holder: Option<Box<Holder>>
}

#[derive(Debug)]
struct City {
    name: String,
    population: u32,
    city_history: Rc<String>,
}

#[derive(Debug)]
struct CityData {
    names: Vec<String>,
    histories: Vec<Rc<String>>,
}

trait Vehicle {
    fn drive(&self);
}

struct Car;

impl Vehicle for Car {
    fn drive(&self) {
        println!("The car is driving");
    }
}

enum List {
    Cons(i32, Rc<List>),
    Nil,
}

