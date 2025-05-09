
pub struct City<'a> {
    pub name: &'a str, // Here's the problem
    pub date_founded: u32,
}