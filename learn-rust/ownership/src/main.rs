fn main() {
    {
        let s = "Hello world";
        println!("{}", s);
    }

    let mut s = String::from("Hello mutable");
    s.push_str(" world");


    let s1 = String::from("hello000");
    let s2 = &s1;

    println!("{}, world!", s1);

    let ss = String::from("oh booy!");
    let x = 5;
    takes_ownership(ss);
    // print!("{}", ss);
    makes_copy(x);
    println!("{}", x);

    let mut u = String::from("Hello");
    change(&mut u);
    println!("{}", u);

    let so = String::from("hello world");

    let sos = &so[0..5];
    println!("{}", sos);

    // let age;
    //
    // {
    //     let n = 5;
    //     age = &n;
    // }
    // println!("The age is {}", age);
    let string1 = String::from("abcd");
    let string2 = "xyz";

    let result = longest_string(string1.as_str(), string2);
    println!("The longest string is {}", result);
}

fn longest_string<'a>(x: &'a str, y: &'a str) -> &'a str {
    if x.len() > y.len() {
        x
    }
    else {
        y
    }

}

fn takes_ownership(some_string: String) { // some_string comes into scope
    println!("{}", some_string);
} // Here, some_string goes out of scope and `drop` is called. The backing
// memory is freed.

fn makes_copy(some_integer: i32) { // some_integer comes into scope
    println!("{}", some_integer);
} // H

fn change(n: &mut String) {
    n.push_str(" lalaland")
}

