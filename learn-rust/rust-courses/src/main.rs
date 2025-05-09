fn main() {
    println!("Hello, world!");

    let mut num = 0;

    loop {
        println!("{}", num);
        num += 1;

        if num > 4 {
            break;
        }
    }
}
