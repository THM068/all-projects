package com.learning.junit.patterns.iterator;

import com.learning.junit.patterns.iterator.BikeList;

public class Main {

    public static void main(String[] args) {
        BikeList bikeList = new BikeList();

        bikeList.addBike("Cool bike");
        bikeList.addBike("Red bike");
        bikeList.addBike("Blue bike");

        for(String name : bikeList) {
            System.out.println(name);
        }


    }
}
