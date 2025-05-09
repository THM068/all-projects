fn print_elements(elems: &Vec<String>) {
    for elem in elems {
        println!("{}", elem);
    }
    println!("------------------");
    elems.iter()
        .map(|elem| format!("{} {}", elem, elem))
        .for_each(|elem| {
        println!("{}", elem);
    });
}

fn shorten_string(elems: &mut Vec<String>) {
   elems.iter_mut()
        .for_each(|elem| {
            elem.truncate(1)
        });
}
fn find_color_or(colors: &Vec<String>, search: &str, fallback: &str) -> String {
    colors.iter().
        find(|color| color.contains(search))
        .map_or(fallback.to_string(), |color| color.to_string())
}
#[cfg(test)]
mod test {
    use crate::iterators::{print_elements, shorten_string, find_color_or};

    #[test]
    fn iterator_colors() {
        let colors = vec![
            String::from("red"),
            String::from("blue"),
            String::from("yellow"),
        ];

        let mut colors_iter = colors.iter();
        println!("{:?}", colors_iter.next());
        println!("{:?}", colors_iter.next());
        println!("{:?}", colors_iter.next());
        println!("{:?}", colors_iter);

   }
    #[test]
    fn print_Elems() {
        let colors = vec![
            String::from("red"),
            String::from("blue"),
            String::from("yellow"),
        ];
        print_elements(&colors);
        println!("-----------------");
        println!("{:#?}", colors);

        assert_eq!(&colors[1..3], ["blue", "yellow"]);
        println!("{:?}", &colors[1..3]);
    }

    #[test]
    fn shorten_strings() {
        let mut colors = vec![
            String::from("red"),
            String::from("blue"),
            String::from("yellow"),
        ];
        shorten_string(&mut colors);
        println!("{:?}", colors);
    }


    #[test]
    fn find_color_or_fallback() {
        let colors = vec![
            String::from("red"),
            String::from("blue"),
            String::from("yellow"),
        ];

        assert_eq!(find_color_or(&colors, "blue","fallback"), "blue");

    }
}