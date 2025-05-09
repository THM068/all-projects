package com.learning.junit.patterns.flyweight.inventory;

import java.util.HashMap;
import java.util.Map;

public class Catalog {
    private Map<String,Item> items = new HashMap<>();

    public Item lookup(String itemName) {
        if(!items.containsKey(itemName)) {
            items.put(itemName, new Item(itemName));
        }
        return items.get(itemName);
    }
    public int totalItemsMade() {
        return this.items.size();
    }
}
