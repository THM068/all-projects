trait Sum {
    fn summ(&self) -> i32;
}

impl Sum for Vec<i32> {
    fn summ(&self) -> i32 {
        self.iter().sum()
    }
}

#[cfg(test)]
mod test {
    use super::*;

    #[test]
    fn test_sum() {
        let numbers = vec![1, 2, 3, 4, 5];
        assert_eq!(numbers.summ(), 15);
    }
}