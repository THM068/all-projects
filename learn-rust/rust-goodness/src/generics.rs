use num_traits::Float;
use super::basket::Basket;
use super::basket::Container;
fn solve<T: Float> (a: T, b: T) -> T {
    a + b
}
pub struct WrappingU32 {
    value: u32,
}

impl From<u32> for WrappingU32 {
    fn from(value: u32) -> Self {
        WrappingU32 { value }
    }
}
#[cfg(test )]
mod test {
    use chrono::NaiveDate;
    use super::*;
    #[test]
    fn test_into_from() {
        let wrapping: WrappingU32 = 42.into();
        assert_eq!(wrapping.value, 42);
    }

    #[test]
    fn test_solve() {
        let a: f64 = 30.1;
        let b: f64 = 30.1;

        assert_eq!(solve(a, b), 60.2);
    }
    #[test]
    fn test_basket() {
        let mut basket = Basket::new("hello".into());
        assert_eq!(basket.is_empty(), false);
        assert_eq!(basket.get(), Some("hello".into()));
        assert_eq!(basket.is_empty(), true);
        basket.put("world".into());
        assert_eq!(basket.is_empty(), false);
        assert_eq!(basket.get(), Some("world".into()));
    }

}
fn example() {
    let wrapping: WrappingU32 = 42.into();
    let wrapping = WrappingU32::from(42);
}

