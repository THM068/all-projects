package com.master.masterspringboot.aop;

import org.springframework.stereotype.Component;

@Component
public class SampleAdder {

    public int add(int a, int b) {
        return a + b;
    }

    public String getPassenger(String name) {
        System.out.println("I am a passenger");
        return "Thando";
    }

    public  String getPassenger() {
        return "Love you";
    }

    @Log
    public String logMe(int id) {
        if(id < 1) {
           // throw new RuntimeException("id should not be less than 1");
        }
        return "Loggin me";
    }
}
