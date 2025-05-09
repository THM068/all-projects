package main

import "encoding/json"
import "sort"

func ParseTextToProducts(productText string) Products {
	var products Products
	json.Unmarshal([]byte(productText), &products)

	sort.SliceStable(products.Products, func(i, j int) bool {
		return products.Products[i].Name <= products.Products[j].Name
	})

	return products
}
