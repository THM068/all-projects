mod collection;

use std::collections::HashMap;
use std::fs::File;
use serde::Deserialize;
use collection::runVec;

#[derive(Debug)]
enum StatusMessage {
    Ok
}
const CSV_FILE: &str = "cities.csv";
#[derive(Debug)]
struct CubeSat {
    id: u64,
}

fn main() {
    println!("Hello, world!");

    let sat_a = CubeSat { id: 0 };
    let sat_b = CubeSat { id: 1 };
    let sat_c = CubeSat { id: 2 };

    let a_status = check_status(sat_a);
    let b_status = check_status(sat_b);
    let c_status = check_status(sat_c);
    println!("a: {:?}, b: {:?}, c: {:?}", a_status, b_status, c_status);

    let file = File::open(CSV_FILE);
    type Record = HashMap<String, String>;

    if let Ok(f) = file {
        let mut rdr = csv::Reader::from_reader(f);
        // for result in rdr.deserialize() {
        //     match result {
        //         Ok(record) => {
        //             let city: City = record;
        //             println!("{:?}", city);
        //         }
        //         Err(e) => println!("Error: {}", e),
        //     }
        // }
        let records: Vec<City> = rdr.deserialize()
                        .map(|result| result.unwrap())
                       .collect::<Vec<City>>();
        // print!("{:?}", records);

        records.iter().take(3)
            .for_each(|city| {
                println!("{:?}", city)
            });
    } else {
        println!("File not found");
    }

    runVec();



}

fn check_status(sat_id: CubeSat) -> StatusMessage {
    StatusMessage::Ok
}


#[derive(Debug, Deserialize)]
#[serde(rename_all = "PascalCase")]
struct City {
    #[serde(rename = "LatD")]
    LatD: i32,
    #[serde(rename = "LatM")]
    latM: i32,
    #[serde(rename = "LatS")]
    latS: i32,
    #[serde(rename = "NS")]
    ns: String,
    #[serde(rename = "LonD")]
    lonD: i32,
    #[serde(rename = "LonM")]
    lonM: i32,
    #[serde(rename = "LonS")]
    lons: i32,
    #[serde(rename = "EW")]
    ew: String,
    #[serde(rename = "City")]
    city: String,
    #[serde(rename = "State")]
    state: String
}