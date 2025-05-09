fn main() {
    println!("Hello, world!");

    let mut names = vec!["Bob", "Frank", "Ferris"];
    names.sort();
    println!("{:?}", names);

    let mut cities = vec![
        City { name: "lagos".to_string(), population: 21_000_000 },
        City { name: "Kano".to_string(), population: 13_000_000 },
        City { name: "Ibadan".to_string(), population: 5_000_000 },
        City { name: "Abuja".to_string(), population: 3_000_000 },
    ];

    sort_cities(&mut cities);

    for city in cities {
        println!("{:?}", city);
    }
    println!("-------------------------------------------");
    let mut cities = vec![
        City { name: "lagos".to_string(), population: 21_000_000 },
        City { name: "Kano".to_string(), population: 13_000_000 },
        City { name: "Ibadan".to_string(), population: 5_000_000 },
        City { name: "Abuja".to_string(), population: 3_000_000 },
    ];
    sort_in_reverse(&mut cities);
    for city in cities {
        println!("{:?}", city);
    }

}

#[derive(Debug)]
struct City {
    name: String,
    population: i64,
}

fn sort_cities(cities: &mut Vec<City>) {
    cities.sort_by_key(|city| city.population);
}

fn sort_in_reverse(cities: &mut Vec<City>) {
    cities.sort_by(|a, b| b.population.cmp(&a.population));
}
