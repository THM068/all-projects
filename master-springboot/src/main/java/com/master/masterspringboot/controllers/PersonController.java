package com.master.masterspringboot.controllers;

import com.master.masterspringboot.aop.SampleAdder;
import com.master.masterspringboot.aop.realworld.Flight;
import com.master.masterspringboot.configuration.Person;
import com.master.masterspringboot.core.CarBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    private final SampleAdder sampleAdder;
    private final Flight flight;
    private CarBean carBean;
    private String poodle;
    private String dbName;

    @Autowired
    public PersonController(SampleAdder sampleAdder, Flight flight, CarBean carBean, String poodle, String dbName) {
        this.sampleAdder = sampleAdder;
        this.flight = flight;
        this.poodle = poodle;
        this.dbName = dbName;

    }

    @GetMapping(value = "/person", produces = "application/json")
    public Person person() {
        sampleAdder.add(2,4);
        sampleAdder.getPassenger();
        sampleAdder.logMe(0);
        this.flight.getId();
        this.carBean = carBean;
        return new Person("Thando " + poodle);
    }

    @GetMapping(value = "/person/spel")
    public String spel() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("'Hello World'.concat('!')");
        String value = (String)expression.getValue();
        return "Hello world " + value;
    }

    @GetMapping(value = "/person/props")
    public String props() {
        return this.dbName;
    }
}
