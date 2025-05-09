#[cfg(test)]
mod test {
    #[test]
    fn test() {
        let nums = vec![1, 2, 3, 4, 5].iter().product::<i32>();
        assert_eq!(nums, 120);
    }

    #[test]
    fn test_palindromw() {
        let mut palindrome = vec!["a man", "a plan", "a canal","panama"];
        palindrome.reverse();
        assert_eq!(palindrome, vec!["panama", "a canal", "a plan", "a man"]);

    }
}
