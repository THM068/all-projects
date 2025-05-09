package com.master.masterspringboot.aop.realworld;

public class Passenger {

    private final String name;
    private final String country;

    public Passenger(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

}
