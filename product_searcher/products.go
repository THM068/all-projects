package main

type Products struct {
	Products []Product `json:"products"`
}
type Product struct {
	Name     string  `json:"name""`
	Quantity uint    `json:"quantity""`
	Price    float64 `json:"price",string"`
}

func TT() {

}
