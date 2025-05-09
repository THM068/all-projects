use std::collections::HashMap;

fn add_two(x: i32, y: i32) -> i32 {
    x + y
}

fn calculate_and_print(x: i32, y: i32, f: fn(i32, i32)-> i32) {
    let result = f(x,y);
    println!("Result: {}", result);
}

fn calculate_and_print_closure(x: i32, y: i32, f: Box<dyn Fn(i32, i32) -> i32 + '_>) {
    let result = f(x,y);
    println!("Result: {}", result);
}
#[cfg(test)]
mod test {
    use crate::closures::{add_two, Cacher, calculate_and_print, calculate_and_print_closure, Country, Person};

    #[test]
    fn test_closures() {
        let add = |x, y| x+ y;

        let result = add;
        calculate_and_print(1,2, add_two);
        calculate_and_print(2,4, |x,y| x+y);

        let z = 100;
        calculate_and_print_closure(2,4, Box::new(|x,y| x+y + z) );

        assert_eq!(result(1,2), 3);
    }

    #[test]
    fn sort_countries_by_population() {
        let countries = vec![
            Country { name: "India".to_string(), population: 1_400_000_000 },
            Country { name: "USA".to_string(), population: 330_000_000 },
            Country { name: "Canada".to_string(), population: 38_000_000 },
            Country { name: "UK".to_string(), population: 67_000_000 },
        ];

        let mut countries = countries;
        countries.sort_by(|a, b| a.population.cmp(&b.population));
        assert_eq!(countries[0].name, "Canada");
        assert_eq!(countries[1].name, "UK");
        assert_eq!(countries[2].name, "USA");
        assert_eq!(countries[3].name, "India");
    }

    #[test]
    fn test_mutable_closure() {
        let mut person = Person { name: "John".into() };

        let  mut change_name  = || person.name = "Jane".into();

        change_name();
        assert_eq!(person.name, "Jane");
    }

    #[test]
    fn test_cacher() {
        let mut cacher = Cacher::new(|x| x + 1);
        assert_eq!(cacher.value(1), 2);
        assert_eq!(cacher.value(1), 2);
        assert_eq!(cacher.value(2), 3);
        assert_eq!(cacher.value(2), 3);

    }
}

fn apply_and_log(func: impl FnOnce(i32) -> i32, func_name: &str, input: i32) {
    println!("Calling {func_name}({input}): {}", func(input))

}
struct Country {
    name: String,
    population: u32,
}

struct Person {
    name: String,
}

struct Cacher<T> where T: Fn(i32) -> i32 {
    calculation: T,
    value: HashMap<i32, i32>,
}

impl <T> Cacher<T>  where T: Fn(i32) -> i32  {
    fn new(calculation: T) -> Cacher<T> {
        Cacher {
            calculation,
            value: HashMap::<i32,i32>::new(),
        }
    }

    fn value(&mut self, arg: i32) -> i32 {
        if let Some(value) = self.value.get(&arg) {
            println!("Returning cached value for {}", arg);
            *value
        }
        else {
            println!("Calculating value for {}", arg);
            let value = (self.calculation)(arg);
            self.value.insert(arg, value);
            value
        }
    }


    
}