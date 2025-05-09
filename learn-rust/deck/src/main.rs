use std::error::Error;
use rand::{seq::SliceRandom, thread_rng};

fn main()  {
    let mut deck = Deck::new();
    deck.shuffle();
    println!("My deck is {:#?}", deck);

}

#[derive(Debug)]
struct Deck {
    cards: Vec<String>,
}

impl Deck {
    fn new() -> Self {
        let suits = ["Hearts", "Diamonds", "Clubs"];
        let values = ["Ace", "Two", "Three"];
        let mut cards = vec![];

        for suit in suits.iter() {
            for value in values.iter() {
                let card = format!("{} of {}", value, suit);
                cards.push(card);
            }
        }
        Deck { cards }
    }

    fn shuffle(&mut self) {
        let mut rng = thread_rng();
        self.cards.shuffle(&mut rng);
    }

    fn deal(&mut self, num_cards: usize) -> Vec<String> {
        self.cards.split_off(self.cards.len() - num_cards)
    }
}
#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_new_deck() {
        let deck = Deck::new();
        assert_eq!(deck.cards.len(), 9);
    }

    #[test]
    fn test_shuffle_deck() {
        let mut deck = Deck::new();
        let original_deck = deck.cards.clone();
        deck.shuffle();
        assert_ne!(deck.cards, original_deck);
    }

    #[test]
    fn test_deal_deck() {
        let mut deck = Deck::new();
        let original_deck = deck.cards.clone();
        let dealt_cards = deck.deal(3);
        assert_eq!(dealt_cards.len(), 3);
        assert_eq!(deck.cards.len(), 6);
        assert_eq!(deck.cards, original_deck[0..6].to_vec());
    }
}
