package main

import "testing"

func TestNames(t *testing.T) {
	name, _ := Names()

	if name != "Thando" {
		t.Error("Name not equal to expected variable")
	}
}
