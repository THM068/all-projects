package main

import (
	"sort"
)

type ProductSearcher struct {
	products Products
}

func (p ProductSearcher) search(searchTerm string) (*Product, bool) {
	idx := sort.Search(len(p.products.Products), func(x int) bool {
		return p.products.Products[x].Name == searchTerm
	})

	if idx == len(p.products.Products) {
		return nil, false
	}
	return &p.products.Products[idx], true

}
