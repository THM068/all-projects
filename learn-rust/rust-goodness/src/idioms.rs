#[cfg(test)]
mod test {

    #[test]
    fn test_iterating_over_options() {
        let turing = Some("Turing");
        let mut logicians = vec!["Curry", "Kleene", "Markov"];

        logicians.extend(turing);

        assert_eq!(logicians, ["Curry", "Kleene", "Markov", "Turing"]);

        let turing = Some("Turing");
        let logicians = vec!["Curry", "Kleene", "Markov"];

        for logician in logicians.iter().chain(turing.iter()) {
            println!("{logician} is a logician");
        }
    }
}