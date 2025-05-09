mod namespaces;

use std::{env, io};
use std::process::{Command, Stdio};

fn main() {
    let args: Vec<String> = env::args().collect();
    println!("{:?}", args);

    let first_command = &args[2];
    let other_options = &args[3..];
    println!("{:?}", first_command);
    println!("{:?}", other_options);
    let run_command = String::from("run");

    match first_command {
        run_command => {
           Command::new(first_command)
                .args(other_options)
                .stderr(Stdio::piped())
                .spawn()
                .expect("{first_command} failed");
            //println!("process finished with: {status}");
        }
        _ => {
            println!("Command not found");
        }
    }

}
// fn main() -> io::Result<()> {
//     let mut buffer = String::new();
//     io::stdin().read_line(&mut buffer)?;
//     println!("You typed: {}", buffer);
//     Ok(())
// }