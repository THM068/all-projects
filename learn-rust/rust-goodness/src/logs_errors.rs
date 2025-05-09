use std::io::Error;

fn divide(a: f32, b: f32) -> Result<f32, Error> {
    if b == 0.0 {
        return Err(Error::other("Cannot divide by zero"));
    }
    Ok(a / b)
}

fn extract_errors() -> Result<(Vec<String>), Error> {
    let file = std::fs::read_to_string("logs.txt");
    match file {
        Ok(content) => {
            let errors = content.lines().into_iter()
                .filter(|line| line.contains("ERROR"))
                .map(|line| line.to_string())
                .collect();
            Ok(errors)
        },
        Err(e) => Err(e),
    }
}
#[cfg(test)]
mod test {
    use std::fs;
    use super::*;

    #[test]
    fn read_file() {
        let file = fs::read_to_string("logs.txt");
        println!("{:?}", file);
    }

    #[test]
    fn test_divide() {
        assert_eq!(divide(10.0, 2.0).unwrap(), 5.0);
        assert!(divide(10.0, 0.0).is_err());
    }

    #[test]
    fn test_extract_errors() {
        let errors = extract_errors().unwrap();
        fs::write("errors.txt", errors.join("\n")).unwrap();
        println!("{:#?}", errors);
    }

    #[test]
    fn test_string() {
        let name = "자우림";
        println!("My name is actually {}", name);
    }

}