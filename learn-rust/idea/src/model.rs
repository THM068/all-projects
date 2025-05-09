use clap::{Parser, Subcommand};

#[derive(Debug, Parser)]
#[command(author, version, about, long_about = None)]
pub struct Args  {

   #[command(subcommand)]
   pub cmd: Commands
}

#[derive(Subcommand, Debug, Clone)]
pub enum Commands {
   List,

   Solve {
      #[arg(short, long)]
      id: i32,
   },
   Remove{
      #[arg(short,long)]
      id: i32,
   },
   Create{
      #[arg(short = 't',long = "title")]
      title: String,
   },
   Filter{
      #[arg(short,long)]
      state: String,
   }
}


// command]
// create <idea>           Creates and saves a new idea. Example: `idea create "Implement something very cool"`
// list                    Lists all ideas. Example: `idea list`
// filter <filter>         Lists filtered ideas. Example: `idea filter '{"state": "SOLVED"}'`
// solve <id>              Solves an idea. Example `idea solve 1`
// remove <id>             Removes an idea. Example `idea remove 1`
// help                    Prints this help.

// /// Solve the idea
// solve: Option<u32>,
// /// Remove the idea
// remove: Option<u32>,
// /// Filter idead by state
// filter: Option<String>,
// /// Create an idea
// create: Option<String>,
// /// List all ideas