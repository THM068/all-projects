package main

import "fmt"

func main() {
	fmt.Println("Hello World")
	name := "Thando"
	fmt.Println("My name is ", name)

	fmt.Print("Enter a number: ")
	//var input float64
	//fmt.Scanf("%f", &input)
	//
	//output := input * 2
	//
	//fmt.Println(output)

	fmt.Printf("%f", fahrenheitToCelsius(100))

	fmt.Println("For loop")
	i := 0

	for i <= 10 {
		fmt.Println(i)
		i += 1
	}
}

func fahrenheitToCelsius(value float64) (result float64) {
	i := 100 - 32
	r := float64(5) / 9
	fmt.Println(i)
	fmt.Printf(" result %f \n", r)
	resultant := float64(i) * r
	fmt.Printf(" r1 %f", resultant)
	return resultant
}
