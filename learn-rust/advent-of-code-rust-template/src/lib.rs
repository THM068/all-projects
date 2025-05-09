pub fn start_day(day: &str) {
    println!("Advent of Code 2024 - Day {:0>2}", day);
}

// Additional common functions

#[cfg(test)]
mod tests {
    use std::collections::HashMap;
    use super::*;

    #[test]
    fn it_works() {
        start_day("00");
    }

    #[test]
    fn day_one_test_find_total_distance_between_list() {
        let mut list_1: Vec<i32> = vec![];
        let mut list_2: Vec<i32> = vec![];
        let file_list_string: String = std::fs::read_to_string("input/day_1.txt").unwrap();

        file_list_string.lines().for_each(|line| {
            let split:Vec<&str> = line.split("   ").collect();
            list_1.push(split[0].parse::<i32>().unwrap());
            list_2.push(split[1].parse::<i32>().unwrap());
        });
        list_1.sort();
        list_2.sort();

        let result = list_1.into_iter().zip(list_2.into_iter()).collect::<Vec<(i32,i32)>>();

        let result = result.into_iter().map(|(a, b)| (a - b).abs()).sum::<i32>();

        println!("Result: {:?}", result);
    }

    #[test]
    fn day_one_test_similarity_score() {
        let mut list_1: Vec<i32> = vec![];
        let mut list_2: Vec<i32> = vec![];
        let file_list_string: String = std::fs::read_to_string("input/day_1.txt").unwrap();

        file_list_string.lines().for_each(|line| {
            let split:Vec<&str> = line.split("   ").collect();
            list_1.push(split[0].parse::<i32>().unwrap());
            list_2.push(split[1].parse::<i32>().unwrap());
        });
        list_1.sort();
        list_2.sort();

        let result = list_1.into_iter().map(|a| {
            return (list_2.iter().filter(|&b| a == *b).count()) * a as usize;
        }).sum::<usize>();

        println!("Result: {:?}", result);
    }

    #[test]
    fn day_two_find_safe_reports() {
        let file_list_string: String = std::fs::read_to_string("input/day_2.txt").unwrap();
        let mut safe_reports: Vec<bool> = vec![];
        let mut un_safe_reports: Vec<bool> = vec![];
        let mut un_safe_list: Vec<Vec<i32>> = vec![];

        file_list_string.lines().for_each(|line| {
            let split: Vec<i32> = line.split(" ").map(|s| s.parse::<i32>().unwrap()).collect();

            let x:bool = split.windows(2).all(|w| w[0] < w[1] && (w[0] -w[1]).abs() <= 3);
            let y:bool = split.windows(2).all(|w| w[0] > w[1] && (w[0] -w[1]).abs() <= 3);

            if x || y {
                safe_reports.push(true);
            }else {
                   un_safe_list.push(split);
                   let mut check_safe_map: HashMap<&i32, Vec<&i32>> = HashMap::new();
                    un_safe_list.iter().for_each(|s| {
                        s.iter().for_each(|ss| {
                            let list: Vec<&i32> = s.iter().filter(|&x| x != ss).collect();
                            check_safe_map.insert(ss, list);
                        });

                    //let list: Vec<&i32> = s.iter().filter(|&&x| x != s).collect();
                    //check_safe_map.insert(s, list);
                   //    println!("Check safe map: {:?}", check_safe_map);
                   });
                   let all_safe = check_safe_map.values().any(|v| {
                       let x:bool = v.windows(2).all(|w| w[0] < w[1] && (w[0] -w[1]).abs() <= 3);
                       let y:bool = v.windows(2).all(|w| w[0] > w[1] && (w[0] -w[1]).abs() <= 3);
                       x || y
                   });
                   println!("All safe: {:?}", all_safe);
                   if all_safe {
                       safe_reports.push(true);
                   }

            }

        });
        println!("Ssafe reports: {:?}", safe_reports.len());

    }
}
