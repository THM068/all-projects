#[cfg(test)]
mod test {
    //write hello world function

    use crate::client::blnk_client_spec::Deck;

    #[test]
    fn test() {
        assert_eq!("true","true");
        let deck = Deck::new();
        println!("{:?}", deck);

    }
}

#[derive(Debug)]
struct Deck {
    cards: Vec<String>
}

impl Deck {
    fn new() -> Self {
        let suites = ["Hearts", "Diamonds", "Clubs", "Spades"];
        let values = ["Ace", "Two", "Three", "four"];
        let mut cards = vec![];

        for suit in suites.into_iter() {
            for value in values.into_iter() {
                cards.push(format!("{} of {}", value, suit));
            }
        }

        Self {
            cards
        }
    }
}

