package main

import (
	"fmt"
	"testing"
)

func TestParseText(t *testing.T) {
	input := "{\n  \"products\" : [\n    {\"name\": \"Widget\", \"price\": 25.00, \"quantity\": 5 },\n    {\"name\": \"Thing\", \"price\": 15.00, \"quantity\": 5 },\n    {\"name\": \"Doodad\", \"price\": 5.00, \"quantity\": 10 }\n  ] }"

	var products = ParseTextToProducts(input)

	if len(products.Products) != 3 {
		t.Error("There should be 3 products")
	}

	productSearcher := ProductSearcher{
		products: products,
	}

	product, found := productSearcher.search("Thing")
	fmt.Println(products.Products)
	fmt.Println("The Thing product is ", found)
	if found {
		fmt.Println("The product name is", product.Name)
	}

}
