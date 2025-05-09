

#[derive(Debug)]
pub enum UsState {
    Alabama,
    Alaska,
}
pub enum Coin {
    Penny,
    Nickel,
    Dime,
    Quarter(UsState)
}

impl Coin {
    pub fn value_in_cents(&self) -> u8 {
        match self {
            Coin::Penny => 1,
            Coin::Nickel => 5,
            Coin::Dime => 10,
            Coin::Quarter(state) =>{
                println!("The quarter belongs to state of {:?}", state);
                25
            }

        }
    }
}
pub enum TrafficLight {
    Red,
    Yellow,
    Green,
    Magento { color: String}
}

impl TrafficLight {
    pub fn display_color(&self) -> &str {
        match self {
            TrafficLight::Green => "Green",
            TrafficLight::Red => "Red",
            TrafficLight::Yellow => {
                "Yellow"
            },
            TrafficLight::Magento { color} =>
               color
        }
    }
}