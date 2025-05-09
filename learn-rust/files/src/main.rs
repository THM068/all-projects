use std::fs::File;
use std::fs;
use std::io::Write;

#[allow(unused_variables)]
fn main() {
    l
    let fileName = "hello.txt";
    let file = File::open(&fileName);
    println!("The file name is {}", fileName);

    match file {
        Ok(file) => {
            println!("The file is open ")
        },
        Err(error) => println!("Failed to open the file: {:?}", error)
    }

    let content = fs::read_to_string(&fileName).expect("failed to read file");
    println!("The content of the file {}", content);

    let mut outputFile = File::create("output.txt").expect("failed to create file");
    outputFile.write_all(b"Hello I am blue").expect("failed to write to file");
    println!("File written successfully");
}
