use polars::prelude::*;
use std::fs::{self, File};
use std::path::Path;
use polars::io::ipc::{IpcReader, IpcWriter};
const SESSION_PATH: &str = ".datafx_session.arrow";

pub fn session_exists() -> bool {
    Path::new(SESSION_PATH).exists()
}

pub fn clear_session() {
    let _ = fs::remove_file(SESSION_PATH);
    println!("Session cleared");
}

pub fn start_session(input: &str) -> Result<(), Box<dyn std::error::Error>> {
    let csv_reader_result = CsvReadOptions::default()
        .try_into_reader_with_file_path(Some(input.into()));

    let Ok(csv_reader) = csv_reader_result else {
        println!("Error reading CSV file");
        return Err(Box::new(std::io::Error::new(
            std::io::ErrorKind::Other,
            "Error reading CSV file",
        )));
    };
    let mut df = csv_reader.finish()?;
    save_session(&mut df)?;
    println!("Session started with df {}", df);
    Ok(())
}

pub fn save_session(df: &mut DataFrame) -> Result<(), PolarsError> {
    let file = File::create(SESSION_PATH)?;
    IpcWriter::new(file).finish(df)?;
    Ok(())
}

pub fn load_session() -> Result<DataFrame, PolarsError> {
    let file = File::open(SESSION_PATH)?;
    IpcReader::new(file).finish()
}
pub fn commit_session(output: &str) -> Result<(), Box<dyn std::error::Error>> {
    let mut df = load_session()?;
    let mut output_file_result = std::fs::File::create(output);

    let Ok(output_file) = output_file_result else {
        println!("Error creating output file");
        return Err(Box::new(std::io::Error::new(
            std::io::ErrorKind::Other,
            "Error creating output file",
        )));
    };
    CsvWriter::new(output_file).finish(&mut df)?;
    clear_session();
    println!("Session committed to {}", output);
    Ok(())
}
mod test {
    use super::*;
    use std::fs::File;
    use polars::prelude::*;

   #[test]
   fn test_session_exists() {
       let exists = session_exists();
       assert_eq!(exists, false);

       // Create a session file for testing
       let _ = File::create(SESSION_PATH);
       let exists = session_exists();
       assert_eq!(exists, true);

       // Clean up
       clear_session();
       assert_eq!(session_exists(), false);
   }


    #[test]
    fn test_session() {
         let input = "test.csv";
         let output = "output.csv";

        // Start a session
        start_session(input).unwrap();

        //Load the session
        let df = load_session().unwrap();
        assert_eq!(df.shape(), (7, 5));
        //
        // // Commit the session
        commit_session(output).unwrap();
        //
        // // Clear the session
        clear_session();
    }
}
