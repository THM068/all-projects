package com.master.masterspringboot.aop.realworld;

import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class Flight {
    private String id = "1234555";
    private String company;
    private List<Passenger> passengers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }
}
