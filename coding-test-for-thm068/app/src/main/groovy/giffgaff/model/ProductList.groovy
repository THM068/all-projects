package giffgaff.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import groovy.transform.CompileStatic;


@CompileStatic
@JsonIgnoreProperties(ignoreUnknown = true)
class ProductList {

    List<Product> products
}
