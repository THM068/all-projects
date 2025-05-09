package com.learning.junit.patterns.flyweight.inventory;

public class Item {

    private final String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                '}';
    }

}
