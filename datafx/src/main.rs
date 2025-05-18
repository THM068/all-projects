mod commands;
mod session;

use clap::{command, Arg};
use crate::commands::command_list::command_list;

fn main() {
    println!("Hello, world!");
    let temp_dir = std::env::temp_dir();
    println!("Temp dir: {:?}", temp_dir);

    let arg_matches = command_list()
        .get_matches();
}
