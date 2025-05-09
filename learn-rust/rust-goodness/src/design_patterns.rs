
struct Person {
    name: String,
    age: u32,
}

impl Person {
    fn new<T>(name: T, age: u32) -> Self
    where
        T: Into<String>,
    {
        Self {
            name: name.into(),
            age,
        }
    }

    fn with_name(&mut self, name: String){
        self.name = name;
    }

    fn with_age(&mut self, age: u32){
        self.age = age;
    }

    fn build(self) -> Person {
        self
    }
}