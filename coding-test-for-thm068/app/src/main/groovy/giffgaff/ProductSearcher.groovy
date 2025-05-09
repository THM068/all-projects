package giffgaff

import giffgaff.model.Product
import giffgaff.model.ProductList
import giffgaff.model.ProductResult

class ProductSearcher {
    private final ProductList productList

    ProductSearcher(ProductList productList) {
        if(Objects.isNull(productList) || Objects.isNull(productList.products)) {
            throw new IllegalArgumentException("Invalid product List")
        }
        this.productList = productList
    }

    ProductResult search(String productName) {
       Product product = productList.products.find { it.name.equals(productName)}
       return new ProductResult(product: product, found: product != null)
    }
}
