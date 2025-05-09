package com.learning.junit.patterns.flyweight.inventory;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InventorySystem {

    private final Catalog catalog = new Catalog();
    private final List<Order> orders = new CopyOnWriteArrayList<>();

    public void takeOrder(String itemName, int orderNumber) {
        Item item = catalog.lookup(itemName);
        orders.add(new Order(item, orderNumber));
    }

    public void process() {
        for(Order order: orders) {
            order.processOrder();
            orders.remove(order);
        }
    }

    public String report() {
        return "\n Total objects made " + catalog.totalItemsMade();
    }
}
