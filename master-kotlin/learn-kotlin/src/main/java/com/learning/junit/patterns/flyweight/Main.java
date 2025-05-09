package com.learning.junit.patterns.flyweight;

import com.learning.junit.patterns.flyweight.inventory.InventorySystem;

public class Main {

    public static void main(String[] args) {
        Integer firstInt = Integer.valueOf(5);
        Integer secondInt = Integer.valueOf(5);
        Integer differentInt = new Integer(5);
        Integer thirdInt = Integer.valueOf(10);

        System.out.println(System.identityHashCode(firstInt));
        System.out.println(System.identityHashCode(secondInt));
        System.out.println(System.identityHashCode(differentInt));
        System.out.println(firstInt == secondInt);
        System.out.println(firstInt == differentInt);
        System.out.println(System.identityHashCode(thirdInt));

        InventorySystem is = new InventorySystem();
        is.takeOrder("Cofee", 23);
        is.takeOrder("Cofee", 33);
        is.takeOrder("Tea", 25);
        is.takeOrder("Cats", 27);
        is.takeOrder("Movies", 27);
        is.takeOrder("Alabama", 27);

        System.out.println(is.report());
    }
}
