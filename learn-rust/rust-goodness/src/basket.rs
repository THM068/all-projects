pub trait Container<T> {
    fn get(&mut self) -> Option<T>;
    fn put(&mut self, title: T);
    fn is_empty(&self) -> bool;
}
pub struct Basket<T> {
    pub items: Option<T>
}

impl Basket<String> {
    pub fn new(title: String) -> Self {
        Basket {
            items: Some(title)
        }
    }
}

impl <T>Container<T> for  Basket<T> {

    fn get(&mut self) -> Option<T> {
        self.items.take()
    }

    fn put(&mut self, title: T) {
        self.items = Some(title);
    }

    fn is_empty(&self) -> bool {
        self.items.is_none()
    }
}