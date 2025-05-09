package com.master.masterspringboot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AppBeanConfiguration {

    @Bean
    public Dog tdawg() {
        return new Dog();
    }

    @Bean
    public String poodle(@Value("#{tdawg.getName()}") String name) {
        return name;
    }
}


class Dog {
    private String name = "Dog Master";


    public String getName() {
        return this.name;
    }
}
