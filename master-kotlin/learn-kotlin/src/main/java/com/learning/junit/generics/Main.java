package com.learning.junit.generics;

public class Main {

    public static void main(String[] args) {
        OrderPairs<String,String> pair = new OrderPairs<>("name", "Thando");
        System.out.println(pair.getKey());
        System.out.println(pair.getValue());
    }

}

interface Pair<K,V> {
    K getKey();
    V getValue();
}

class OrderPairs<K,V> implements  Pair<K,V> {
    private K key;
    private V value;

    public OrderPairs(K key, V value) {
        this.key = key;
        this.value = value;
    }
    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }
}
