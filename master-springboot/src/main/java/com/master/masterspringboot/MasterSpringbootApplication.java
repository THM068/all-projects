package com.master.masterspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class MasterSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MasterSpringbootApplication.class, args);
    }



}
