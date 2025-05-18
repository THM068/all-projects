use clap::{Arg, Command, command};

pub fn command_list() -> Command {
    command!()
        .about("Data fx is a cli tool to analyze and clean csv tsv files")
        .arg(
            Arg::new("firstname")
                .short('f')
                .long("firstname")
                 .required(true)
                .help("First name of the user")
        )
        .arg(
            Arg::new("lastname")
                .short('l')
                .long("lastname")
                .help("Last name of the user")

        )
}