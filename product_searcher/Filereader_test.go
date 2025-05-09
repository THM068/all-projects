package main

import "testing"

func TestFileReader(t *testing.T) {
	expected := "{\n  \"products\" : [\n    {\"name\": \"Widget\", \"price\": 25.00, \"quantity\": 5 },\n    {\"name\": \"Thing\", \"price\": 15.00, \"quantity\": 5 },\n    {\"name\": \"Doodad\", \"price\": 5.00, \"quantity\": 10 }\n  ] }"
	var f = FileReader{FileLocation: "/Users/tma24/private_projects/product_searcher/app/resources/product_list.json"}
	content := f.getContent()
	if content != expected {
		t.Error("File content does not match")
	}
}
