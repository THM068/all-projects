package com.learning.junit.patterns.flyweight.inventory;

public class Order {

    private final Item item;
    private final int orderNumber;

    public Order(Item item, int orderNumber) {
        this.item = item;
        this.orderNumber = orderNumber;
    }

    public void processOrder() {
        System.out.println("Ordering " + item + " for oder " + orderNumber);
    }



}
