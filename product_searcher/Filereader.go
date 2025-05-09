package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
)

type FileReader struct {
	FileLocation string
}

func (f *FileReader) getContent() (result string) {
	result = ""
	jsonFile, err := os.Open(f.FileLocation)
	defer jsonFile.Close()
	if err != nil {
		log.Println("Failure to open the file ", err)
		return result
	}

	//fmt.Print(jsonFile.Name())
	if err == nil {
		fmt.Println("Here ....")
		content, contentError := ioutil.ReadAll(jsonFile)
		if contentError == nil {
			result = string(content)
		}

	}
	return
}
