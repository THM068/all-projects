package com.master.masterspringboot.configuration;

import org.springframework.core.convert.converter.Converter;

public class PersonConverter implements Converter<String, Person> {
    @Override
    public Person convert(String source) {
        return new Person(source);
    }
}
