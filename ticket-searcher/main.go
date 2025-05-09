package main

import (
	"fmt"
	"os"
)
import "log"

func main() {
	var conferenceName = "Go Conference"
	const conferenceTickets = 50
	fmt.Printf("Welcome to our %v booking application\n", conferenceName)
	fmt.Println("Get your tickets here to attend!")

	var sentence = fmt.Sprintf("Hello, %s and %s", "Thomas", "Alexandra")

	fmt.Println(sentence)

	fmt.Println("The sum of 10 + 5 =", sum(10, 5))

	thando, kerrie := Names()

	fmt.Println(thando, kerrie)
	os.Exit(0)
	log.Println("Hello world")

}

func sum(a int, b int) int {
	return a + b
}

func Names() (string, string) {
	return "Thando", "Kerrie"
}
