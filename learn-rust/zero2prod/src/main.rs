mod lifetimes;

use std::u8;

fn main() {
    println!("The smalled u8 is {}", u8::MIN);
    println!("The smalled u8 is {}", u8::MAX);
    println!("The smalled i8 is {}", i8::MIN);
    println!("The smalled i8 is {}", i8::MAX);

    let brian = true;
    println!("Brian always says {brian}");
    let brian = "I am a string".to_string();
    println!("Brian always says {brian}");

    let name = "자우림";
    let other_name = String::from("Adrian Fahrenheit Țepeș");
    println!("{} is a cool name", name);
    println!("{} is a cool other_name", other_name);

    let size_of_name = std::mem::size_of_val(name);
    println!("The size of name is {} bytes", size_of_name);
    let v = return_str();
    println!("The size of name is {} bytes", v);

    let country = String::from("Austria");
    let country_ref = &country;
    let country = 8;
    println!("{country_ref}-{country}");


    fn work() -> &'static str {
        "I am a static string"
    }
    work();

    let nums = (1..).take(10).collect::<Vec<i32>>();
    println!("{:?}", nums);

    let mut scores = vec![1, 2, 3, 4, 5];
    println!("{:?}", scores);
    for score in scores.iter_mut() {
        *score += 1;
        println!("{}", score);
    }

    let newScores = scores.iter().map(|x| x+3).collect::<Vec<i32>>();
    println!("{:?}", newScores);

    let my_vec = vec!['a', 'b', '거', '柳'];

    let mut iter = my_vec.iter();

    assert_eq!(&'a', iter.next().unwrap());
    assert_eq!(&'b', iter.next().unwrap());
    assert_eq!(&'거', iter.next().unwrap());
    assert_eq!(&'柳', iter.next().unwrap());

    let mut my_library = Library::new("Calgary");
    println!("{my_library:?}");

    my_library.add_book("The Doom of the Darksword");
    my_library.add_book("Demian - die Geschichte einer Jugend");
    my_library.add_book("구운몽");
    my_library.add_book("吾輩は猫である");

    for item in my_library.get_books() {
        println!("{item}");
    }

    let months = vec!["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    let filtered_months = months.into_iter()
        .filter(|m| m.len() < 5)
        .filter(|m| m.contains("u"))
        .collect::<Vec<&str>>();

    println!("filtered_months {:?}", filtered_months);

    let a = [1, 2, 3];

    assert!(a.iter().any(|&x| x != 2));

    let user_input = vec!["8.9", "Nine point nine five", "8.0", "7.6", "eleventy-twelve"];

    let successful_values: Vec<f32> = user_input.into_iter()
        .filter_map(|s| s.parse().ok())
        .collect();
    println!("{:?}", successful_values);

    let events = [
        "Went to grocery store",
        "Came home",
        "Fed cat",
        "Fed cat again",
    ];

    let emptyEvents = CombinedEvents {
        num_of_events: 0,
        data: vec![],
    };

    let combinedEvents = events.iter().fold(emptyEvents, |mut total_events, next_event| {
        total_events.num_of_events += 1;
        total_events.data.push(next_event.to_string());
        total_events
    });

    println!("{combinedEvents:?}");

    let mut number_iter = [7, 8, 9, 10].into_iter();

    let first_two = number_iter.by_ref().take(2).collect::<Vec<_>>();
    let second_two = number_iter.take(2).collect::<Vec<_>>();

    println!("{:?}", first_two);
    println!("{:?}", second_two);
    println!("----------CHUNK-------------");
    let num_vec = vec![1, 2, 3, 4, 5, 6, 7];

    for chunk in num_vec.chunks(3) {
        println!("{:?}", chunk);
    }
    println!("-----------WINDOW------------");
    for window in num_vec.windows(3) {
        println!("{:?}", window);
    }

    let my_number = 8;
    dbg!(my_number);

    let my_box = Box::new(8);
    let an_integer = *my_box;

    println!("{an_integer}");

    let myy_number = Box::new(String::from("8"));
    takes_any_value(myy_number.clone());
    takes_any_value(myy_number);
}

fn takes_any_value<T>(v: T) {}

fn sendOwner(v: String) {}

fn return_str() -> String {
    String::from("Austria")

}

#[derive(Debug)]
struct Library {
    name: String,
    books: BookCollection,
}
#[derive(Debug, Clone)]
struct BookCollection {
    books: Vec<String>,
}

impl Library {
    fn new(name: &str) -> Library {
        Library {
            name: name.to_string(),
            books: BookCollection { books: Vec::new()}
        }
    }

    fn add_book(&mut self, book: &str) {
        self.books.books.push(book.to_string());
    }

    fn get_books(&self) -> BookCollection {
        self.books.clone()
    }
}

impl Iterator for BookCollection {
    type Item = String;

    fn next(&mut self) -> Option<String> {
        match self.books.pop() {
            Some(book) => {
                println!("Accessing book: {book}");
                Some(book)
            }
            None => {
                println!("Out of books at the library!");
                None
            }
        }
    }
}

struct Company {
    name: String,
    ceo: Option<String>,
}

#[derive(Debug)]
struct CombinedEvents {
    num_of_events: u32,
    data: Vec<String>,
}

