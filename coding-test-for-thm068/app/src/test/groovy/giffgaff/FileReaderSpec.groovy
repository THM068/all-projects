package giffgaff

import giffgaff.exceptions.ResourceNotFoundException
import spock.lang.Specification

class FileReaderSpec extends Specification {
    private static final PRODUCT_JSON_FILE = "product_list.json"

    def 'Can read a file from the resources folder'() {
        given:
            FileReader fileReader = new FileReader(PRODUCT_JSON_FILE)
        when:
            String result = fileReader.getContent()
        then:
            result == content
        where:
            content = """{
  "products" : [
    {"name": "Widget", "price": 25.00, "quantity": 5 },
    {"name": "Thing", "price": 15.00, "quantity": 5 },
    {"name": "Doodad", "price": 5.00, "quantity": 10 }
  ] }"""
    }

    def 'Throws resource not found exception when file does not exist'() {
        given:
            FileReader fileReader = new FileReader("non-existent-file.json")
        when:
            fileReader.getContent()
        then:
            thrown(ResourceNotFoundException)
    }
}
