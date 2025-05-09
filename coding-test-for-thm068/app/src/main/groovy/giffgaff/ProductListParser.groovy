package giffgaff

import com.fasterxml.jackson.databind.ObjectMapper
import giffgaff.model.ProductList

class ProductListParser extends TextParser<ProductList> {

    final ObjectMapper objectMapper = new ObjectMapper()

    @Override
    ProductList parse(String textContent) {
        return objectMapper.readValue(textContent, ProductList)
    }
}
