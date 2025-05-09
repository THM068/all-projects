package giffgaff

import giffgaff.model.Product
import giffgaff.model.ProductList
import giffgaff.model.ProductResult
import spock.lang.Specification
import spock.lang.Unroll

class ProductSearcherSpec extends Specification {

    @Unroll
    def "Returns expected result given a search term"() {
        given:
            ProductSearcher productSearcher = new ProductSearcher(productList())
        when:
            ProductResult productResult = productSearcher.search(searchTerm)
        then:
            productResult.found == found
            productResult.product == expectedProduct

        where:
            searchTerm | found | expectedProduct
                "Thing"| true  | productList().products[1]
                "none" | false | null
    }

    def "throws illegalArgument exception when ProductList is null"() {
        when:
            new ProductSearcher(null)
        then:
            thrown(IllegalArgumentException)
    }

    def "throws illegalArgument exception when products in productList is null"() {
        when:
        new ProductSearcher(new ProductList())
        then:
            thrown(IllegalArgumentException)
    }

    private ProductList productList() {
        List<Product> products = [
                new Product(["name": "Widget", "price": 25.00, "quantity": 5 ]),
                new Product(["name": "Thing", "price": 15.00, "quantity": 5 ]),
                new Product(["name": "Doodad", "price": 5.00, "quantity": 10 ]),
        ]
        return new ProductList(products: products)
    }
}
