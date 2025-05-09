package giffgaff.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode

@CompileStatic
@EqualsAndHashCode
class Product implements Comparable<Product> {
    String name
    Double price
    Integer quantity

    @Override
    int compareTo(Product o) {
        return o.name.compareTo(name)
    }
}
