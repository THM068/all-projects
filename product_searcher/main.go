package main

import "fmt"
import "reflect"

func main() {

	var fileReader = FileReader{FileLocation: "/Users/tma24/private_projects/product_searcher/resources/product_list.json"}
	var content = fileReader.getContent()
	var products = ParseTextToProducts(content)
	var search = (&ProductSearcher{products: products})
	var searchTerm string
	var promptUser bool = true
	var name = "Reflection type"
	fmt.Println(reflect.TypeOf(name))

	fmt.Print("What is the product name? ")
	for promptUser == true {
		fmt.Scan(&searchTerm)
		var product, found = search.search(searchTerm)
		if found {
			fmt.Println("Name: ", product.Name)
			println("Price: ", product.Price)
			println("Quantity: ", product.Quantity)
			promptUser = false
		} else {
			fmt.Println("Sorry, that product was not found in our inventory.")
			fmt.Print("What is the product name?")
		}
	}
}
