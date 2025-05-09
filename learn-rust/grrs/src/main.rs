use ::std::fs::File;
use std::io::{BufRead, BufReader};

use anyhow::Result;
use clap::Parser;

#[allow(unused_variables)] /// Search for a pattern in a file and display the lines that contain it.
#[derive(Parser,Debug)]
#[clap(author="Thando", version, about="A Very simple Package Hunter")]
struct Cli {
    #[arg(short ='n', long = "name")]
    name: Option<String>,
    pattern: String,
    path: std::path::PathBuf,
}
//, Box<dyn std::error::Error>
fn main() -> Result<()>{
    println!("Hello grrs");
    let args = Cli::parse();

    println!("{:?}", args.name);

    let f = File::open(&args.path);

    if let Ok(opened_file) = f {
        let reader_lines = BufReader::new(opened_file).lines();
        for line in reader_lines {
            if let Ok(content) = line {
                if content.contains(&args.pattern) {
                    println!("{}", content);
                }
            }
        }
    }
    else {
        eprintln!("Oh noes: failed to open file");
    }

    Ok(())
}

// let pattern = std::env::args().nth(1).expect("no pattern given");
// let path = std::env::args().nth(2).expect("no path given");
//
// let cli = Cli {
// pattern: pattern,
// path: std::path::PathBuf::from(path)
// };
