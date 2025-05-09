// use std::env;
//
//
// use Coin::Quarter;
// use enums::TrafficLight;
// use UsState::Alaska;
//
// use crate::enums::{Coin, UsState};
//
// mod enums;
// mod networks;
//
// //slice
// fn main() {
//     const MAX_AGE: u32 = 42;
//     println!("My  age is {}", MAX_AGE);
//     let arr = [10,20,30,40];
//     println!("the array values {:?}", arr);
//     // look at that &
//     let res = sum(&arr);
//     println!("sum {}", res);
//
//     //slices
//     let names = ["thomas", "alex", "chrissy", "mummy", "daddy"];
//     let slice1 = &names[0..2];
//     println!("slice1 value is {:?}", slice1);
//
//     let slice2 = &names;
//     let first = slice2.get(0);
//     let v = if(first.is_some()) {
//         *first.unwrap()
//     } else {
//         let xx = "";
//         xx
//     };
//
//     println!("The value is {}", v);
//
//     let guess: u32 = "42".parse().expect("Not a number!");
//     println!("guess {}", guess);
//
//     let name = "Thando Mafela";
//     printme(&name);
//
//     let guess: u32 = "42".parse().expect("This is not a number");
//     println!("The guess value is {}", guess);
//
//
//     let name = String::from("Peter");
//     let age = 34;
//     let person = Person { name, age};
//
//     println!("The person {:?}", person);
//
//     printPerson(person);
//
//     for a in 'a'..='z' {
//         println!("{}", a)
//     }
//
//     let sum = 4 + 23;
//     println!("The sum is {}", sum);
//
//     let tup: (i32, f64) = (23,34.01);
//     println!("The value tuple {}", tup.0);
//     println!("The value tuple {}", tup.1);
//
//     let age = 45;
//
//     let maxAge = {
//         let age = 12;
//         age + 45
//     };
//
//     println!("The maxAge is {}", maxAge);
//
//     let message = welcome_message();
//     println!("{}", message);
//
//     let res = plus_one(30);
//     print!("The value is {}", res);
//
//     let numbers = [10, 20, 30, 40, 50];
//
//     for n in numbers.iter() {
//         println!("{}", n)
//     }
//
//     let mut mString = String::from("Hello, ");
//     mString.push_str("World");
//
//     println!("The string values is {}", mString);
//
//     let s1 = String::from("hello");
//     let s2 = s1;
//
//     println!("The value of s1 is {}", s2);
//
//     let s1copy = String::from("hello copy");
//     let s2copy = s1copy.clone();
//     println!("s1copy = {}, s2copy = {}", s1copy, s2copy);
//
//     let someString = String::from("some-string");
//     let someInt = 34;
//     takes_ownership(someString.to_owned());
//
//     println!("The value of someString is {}", someString);
//
//     makes_copy(someInt);
//     println!("The value of someInt is {}", someInt);
//
//     let s1 = gives_ownership(); // gives_ownership
// // value into s1.
//     let s2 = String::from("hello"); // s2 comes into
//     let s3 = takes_and_gives_back(s2.to_owned());
//
//     println!("The value is {}", s2);
//
//     let surname = String::from("Mafela");
//     let size = calculateLength(&surname);
//
//     println!("The size of {} is {}", surname, size);
//
//     let mut mutableValue = String::from("I am good ");
//     change(&mut mutableValue);
//     println!("The value of mutableValue is {}", mutableValue);
//
//     let words = String::from("Letter to my last born");
//     let first_word = first_word(&words);
//
//     println!("The first word is {}", first_word);
//
//     let user = User {
//         username: String::from("thm068"),
//         email: String::from("thando@email.com"),
//         active: true,
//         sign_in_count: 34
//     };
//
//     println!("The user is {:?}", user);
//     #[derive(Debug)]
//     struct Color(i32, i32, i32);
//
//     let black = Color(0, 0, 0);
//
//     println!("The color is {:?}", black);
//
//     let rect1 = Rectangle { length: 50, width: 30 };
//     println!("The rectangle {:#?}", rect1);
//     println!("The area of the rectangle is {} square pixels.", rect1.area());
//
//     let rect2 = Rectangle::square(4);
//     println!("The area of the rectangle 2 is {} square pixels.", rect2.area());
//
//     let written = Message::Write("Thando Mafela".to_string());
//     let moveV = Message::Move {x: 12, y: 45};
//     println!("The value of written is {:?}", written);
//     println!("The value of move is {:?}", moveV);
//
//     //enums with a function
//     let red = TrafficLight::Red;
//     println!("The displayed color is {}", red.display_color());
//
//     let magento = TrafficLight::Magento { color: "Magento".to_string()};
//     println!("The displayed magento color is {}", red.display_color());
//
//     //Options example
//     let building: Option<String> = Some(String::from("Halloweean"));
//
//     match building {
//         Some(b) => println!("The building values is {}", b),
//         _ => println!("Nothing")
//     }
//
//     let dime = Coin::Dime;
//     println!("The value of a dime is {}", dime.value_in_cents());
//
//     let quarter = Quarter(Alaska);
//     println!("The value of a quarter is {}", quarter.value_in_cents());
//
//
//     if let TrafficLight::Red = red {
//         println!("The  color is <> RED")
//     };
//
//     println!("Groovy home is {}", env!("GROOVY_HOME"));
//
//     let mut names: Vec<i32> = Vec::new();
//     names.push(1);
//     names.push(2);
//     names.push(3);
//     names.push(4);
//
//     println!("names {:?}", names);
//
//     let mut v: Vec<i32> = vec![1,2,3,4];
//     v.push(5);
//     v.push(6);
//     println!("v {:?}", v);
//
// }
//
//
//
// #[derive(Debug)]
// struct Rectangle {
//     width: u32,
//     length: u32
// }
//
// impl Rectangle {
//     fn area(&self) -> u32 {
//         self.width * self.length
//     }
//
//     fn square(size: u32) -> Rectangle {
//         Rectangle {
//             width: size,
//             length: size
//         }
//     }
// }
// #[derive(Debug)]
// struct Person {
//     name: String,
//     age: u32
// }
// fn printme(name: &str)  {
//     println!("my name is {}", &name);
// }
//
// fn printPerson(person: Person) {
//     print!("The name is {} and age {}", person.name, person.age);
// }
//
// fn sum(values: &[i32]) -> i32 {
//     let mut result = 0;
//     for n in values {
//         result += n
//     }
//     println!("the array values ** {:?}", values);
//     result
// }
//
// fn gives_ownership() -> String {
//     let n = String::from("Hello");
//     n
// }
//
// fn takes_and_gives_back(a_string: String) -> String {
//     a_string
// }
//
// fn welcome_message() -> String {
//     return "Welcome to rust".to_string()
// }
//
// fn plus_one(num: i32) -> i32 {
//     num + 1
// }
//
// fn takes_ownership(some_string: String) { // some_string
//         println!("{}", some_string);
// }
//
// fn makes_copy(some_integer: i32) { // some_integer comes
//         println!("{}", some_integer);
// }
//
// fn calculateLength(s: &String) -> usize {
//     s.len()
// }
//
// fn change(n: &mut String) {
//     n.push_str("World is great")
// }
//
// fn first_word(s: &String) -> &str {
//     let bytes = s.as_bytes();
//
//     for (i, &item) in bytes.iter().enumerate() {
//        if item == b' ' {
//            return &s[0..i];
//         }
//     }
//
//     &s[..]
// }
// #[derive(Debug)]
// struct User {
//     username: String,
//     email: String,
//     sign_in_count: u64,
//     active: bool
// }
//
// fn build_user(username: String, email: String) -> User {
//     User{
//         username,
//         email,
//         active: false,
//         sign_in_count: 1
//     }
// }
// #[derive(Debug)]
// enum Message {
//     Write(String),
//     Move { x: u32, y: u32}
// }
//
