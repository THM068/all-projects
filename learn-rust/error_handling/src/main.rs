// use std::fmt::{Error, Write};
use std::fs;
use std::io::{BufRead, BufReader};
use std::str;

const BUFFER_SIZE: usize = 100;

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let path = "usernames.csv";
    let content = fs::read_to_string(&path)?;

    println!("Content of the file: {}", content);

    //reading binary files
    let binary_content:Vec<u8> = fs::read(&path)?;
    let binary_content_str = str::from_utf8(&binary_content)?;
    println!("Binary content of the file: {}", binary_content_str);

    let usernameFile = fs::File::open(&path)?;

    let buffer_reader = BufReader::new(usernameFile);

    for line in buffer_reader.lines() {
        println!("{}", line?);
    }

    //reading files using buffer and byte
    let binary_file = fs::File::open(&path)?;
    let mut bf_reader = BufReader::with_capacity(BUFFER_SIZE, binary_file);

    loop {
        let buffer: &[u8] = bf_reader.fill_buf()?;
        let buffer_length = buffer.len();

        if buffer.is_empty() {
            break;
        }

        println!("{}", str::from_utf8(buffer)?);
        bf_reader.consume(buffer_length);
    }

   Ok(())
}
