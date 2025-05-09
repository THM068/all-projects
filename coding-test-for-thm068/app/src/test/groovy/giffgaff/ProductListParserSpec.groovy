package giffgaff

import giffgaff.model.ProductList
import spock.lang.Specification

class ProductListParserSpec extends Specification {

    def 'returns a productList when a valid json text is presented'() {
        given:
            ProductListParser productListParser = new ProductListParser()
        when:
            ProductList productList = productListParser.parse(json)
        then:
            productList != null
            productList.products.size() == 3
            productList.products*.name == ["Widget", "Thing", "Doodad"]
            productList.products*.price == [25.00, 15.00, 5.00]
            productList.products*.quantity == [5, 5, 10]

        where:
            json = """{
  "products" : [
    {"name": "Widget", "price": 25.00, "quantity": 5 },
    {"name": "Thing", "price": 15.00, "quantity": 5 },
    {"name": "Doodad", "price": 5.00, "quantity": 10 }
  ] }"""
    }

    def "Returns a ProductList without a products"() {
        given:
            ProductListParser productListParser = new ProductListParser()
        when:
            ProductList productList = productListParser.parse(json)
        then:
         productList.products == null
        where:
            json = """{
      "cars" : [
        {"name": "Porsche", "price": 25.00, "quantity": 5 },
        {"name": "Mac", "price": 15.00, "quantity": 5 },
        {"name": "Mazda", "price": 5.00, "quantity": 10 }
      ] }"""
        }
}
