
use std::fmt::Debug;
use std::fs::File;
use std::io::{BufRead, BufReader};
use clap::{arg, Parser};
use chrono::Local;
#[warn(unused_imports)]
#[derive(Parser)]
#[command(author, version, about, long_about = None)]
struct Args {
    #[arg(short ='w', long = "words")]
    words: Option<std::path::PathBuf>,
    #[arg(short = 'l', long = "lines")]
    lines: Option<std::path::PathBuf>,
    #[arg(short='m')]
    num_of_characters: Option<std::path::PathBuf>
}

// #[derive(Subcommand, Debug, Clone)]
// enum Commands {
//     Get { name: String},
//     Set {
//         key: String,
//         value: String
//     }
// }
fn main() {
    let now = Local::now();
    // println!("The time now is {}", now);
    let cli = Args::parse();

    if let Some(f) = &cli.words {
        let fileToBeOpen = File::open(&f);
        //println!("does file exist {:?}", fileToBeOpen.is_ok());
        match fileToBeOpen {
            Ok(file) => {
                let mut counter = 0;
                let reader_lines = BufReader::new(&file).lines();
                for line in reader_lines {
                    if let Ok(content) = line {
                        counter += content.trim().split(" ").count();
                    }
                }
                println!("{} {:?}", counter, &f);
            },
            Err(error) => println!("error")
        }
    } else if let Some(f) = &cli.lines {
        let fileToBeOpen = File::open(&f);

        match fileToBeOpen {
            Ok(file) => {
                let count_lines = BufReader::new(&file).lines().count();
                println!("{} {:?}", count_lines, &f);
            },
            Err(error) => println!("error")
        }
    }
    else if let Some(f) = &cli.num_of_characters {
        let fileToBeOpen = File::open(&f);
        //println!("does file exist {:?}", fileToBeOpen.is_ok());
        match fileToBeOpen {
            Ok(file) => {
                let reader_lines = BufReader::new(&file).lines();
                let mut count_chars = 0;
                for line in reader_lines {
                    if let Ok(content) = line {
                        for c in content.split(" ") {
                            count_chars += c.len();
                        }
                    }
                }
                println!("{} {:?}", count_chars, &f);
            },
            Err(error) => println!("error")
        }
    }
}

