#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_zip_combine_iterator() {
        let names = vec!["Tony","Adam","Molly"];
        let age   = vec![23, 45, 56 ];

        let combined: Vec<_> = names.into_iter().zip(age.into_iter()).collect();

        assert_eq!(combined, vec![("Tony", 23), ("Adam", 45), ("Molly", 56)]);
    }
}