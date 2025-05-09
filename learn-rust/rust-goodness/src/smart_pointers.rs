use std::rc::Rc;

mod test {
    use std::cell::RefCell;
    use std::collections::HashMap;
    use std::rc::Rc;
    use std::sync;
    use sync::{Arc, Mutex};
    use crate::smart_pointers::Node;

    #[test]
    fn test_arc_mutex() {
        let counter = Arc::new(Mutex::new(HashMap::new()));
        let mut map = counter.lock().unwrap();
        map.insert("hello", "world");
        let name = String::from("hello");
        println!("The pointer value is: {:?}", name.as_ptr());
        assert_eq!(map.get("hello"), Some(&"world"));

        let name = Arc::new("hello".to_string());
        println!("The pointer value is: {:?}", name.as_ptr());
        let n= Arc::clone(&name);
        println!("The pointer value is: {:?}", n.as_ptr());
        println!("{}", Arc::strong_count(&name));
    }

    #[test]
    fn test_rc() {
        let data = Rc::new("hello".to_string());
        let data_clone_1 = Rc::clone(&data);
        let data_clone_2 = Rc::clone(&data);

        println!("Original: {}", data);
        println!("Clone 1: {}", data_clone_1);
        println!("Clone 2: {}", data_clone_2);
        println!("Reference count: {}",
                 Rc::strong_count(&data));

        assert_eq!(Rc::strong_count(&data), 3);
        assert_eq!(data_clone_1.as_ptr(), data_clone_2.as_ptr())
    }

    #[test]
    fn test_rc_using_node() {
        let node_1 = Rc::new(Node {
            value: 1,
            next: None,
        });

        let node_2 = Rc::new(Node {
            value: 2,
            next: Some(Rc::clone(&node_1)),
        });

        let node_3 = Rc::new(Node {
            value: 3,
            next: Some(Rc::clone(&node_2)),
        });

        println!("Node 1: {:?}", node_1);
        println!("Node 2: {:?}", node_2);
        println!("Node 3: {:?}", node_3);
    }

    #[test]
    fn test_arc_example() {
        let data = Arc::new("hello world".to_string());
        let mut handles = vec![];

        for _ in 0..3 {
            let data_clone = Arc::clone(&data);
            let handle = std::thread::spawn(move || {
                println!("Data in thread: {}", data_clone);
            });
            handles.push(handle);
        }

        for handle in handles {
            handle.join().unwrap();
        }
    }

    #[test]
    fn test_ref_cell() {
        let data = RefCell::new(42);

        {
            let mut data_ref_mut = data.borrow_mut();
            *data_ref_mut += 1;
        }
        let data_ref = data.borrow();
        println!("Data: {}", data_ref);
        println!("Data: {:?}", data);
    }


}

#[derive(Debug)]
struct Node {
    value: i32,
    next: Option<Rc<Node>>,
}