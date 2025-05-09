mod model;
mod webcall;

use comfy_table::*;
use clap::{Parser};
use model::Args;
use crate::model::Commands;
use crate::webcall::{create, IdeaWebResponse, remove, list, solve};

fn main() {
    let args = Args::parse();
    println!("Hello, world! {:?}", args);

    match  args.cmd {
        Commands::List => {
            match list() {
                Ok(r) => {
                    print_idea_list(&r);
                }
                Err(error) => {
                    println!("{:?}", error)
                }
            }
        }
        Commands::Solve { id } => {
            println!("Solving the idea {}", &id);
            match solve(id) {
                Ok(idea_web_response) => {
                    println!("Idea solved: {:?}", id);
                    print_idea(&idea_web_response);
                }
                Err(error) => {
                    println!("{:?}", error)
                }
            }
        }
        Commands::Remove { id } => {
            println!("Removing the idea {}", &id);
            match remove(id) {
                Ok(_) => {
                    println!("Idea removed: {:?}", id);
                }
                Err(error) => {
                    println!("{:?}", error)
                }
            }
        }
        Commands::Create { title } => {
            println!("Creating idea: {title}");
            match create(title.as_str()) {
                Ok(idea_web_response) => {
                    println!("Idea created: {:?}", title);
                    print_idea(&idea_web_response);
                }
                Err(error) => {
                    println!("{:?}", error)
                }
            }
        }
        Commands::Filter { state } => {
            println!("list ideas for state: {state}");
        }
    }
}

fn print_idea_list(idea_list: &Vec<IdeaWebResponse>) {
    let mut table = Table::new();
    table.set_header(vec!["Id", "Title", "Solved"]);

    for idea in idea_list.iter() {
        table.add_row(
            vec![
                Cell::new(idea.id),
                Cell::new(idea.title.as_str()),
                Cell::new(idea.solved).fg(Color::Green),
            ]
        );
    }
    println!("{table}");

}

fn print_idea(idea: &IdeaWebResponse) {
    let mut table = Table::new();
    table.set_header(vec!["Id", "Title", "Solved"]);
    table.add_row(
        vec![
            Cell::new(idea.id),
            Cell::new(idea.title.as_str()),
            Cell::new(idea.solved).fg(Color::Green),
        ]
    );
    println!("{table}");
}

