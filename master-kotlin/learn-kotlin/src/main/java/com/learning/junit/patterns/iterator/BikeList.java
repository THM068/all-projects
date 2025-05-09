package com.learning.junit.patterns.iterator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BikeList implements Iterable<String> {
    private List<String> namesList = new ArrayList<>();
    int currentIndex = 0;

    public void addBike(String bike) {
        this.namesList.add(bike);
    }

    @NotNull
    @Override
    public Iterator<String> iterator() {

        return new Iterator<String>() {
            @Override
            public boolean hasNext() {
                return currentIndex < namesList.size() && namesList.get(currentIndex) != null;
            }

            @Override
            public String next() {
                return namesList.get(currentIndex++);
            }
        };
    }
}
