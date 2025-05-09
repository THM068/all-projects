mod test {
    use std::collections::HashMap;

    #[test]
    fn hash_map_test() {
        let num = 34;
        let x = num;

        println!("the pointer is {}", num);
        println!("the pointer is {}", x);

        let mut stock_list: HashMap<String, String> = HashMap::<String,String>::new();

        stock_list.insert("AMZ".into(), "Amazon stock".into());
        stock_list.insert("TSL".into(), "Tesla".into());

        assert_eq!(stock_list.len(), 2);

        stock_list.entry("GOOG".into()).or_insert("Google".into());
        assert_eq!(stock_list.get("GOOG".into()).unwrap(), "Google");

        let amz_stock = stock_list.get_mut("AMZ");

        if let Some(amz) = amz_stock {
            *amz = "Amazon stock info".into()
        }

        assert_eq!(stock_list.get("AMZ").unwrap(), "Amazon stock info");

        println!("{:?}", stock_list);



    }
}