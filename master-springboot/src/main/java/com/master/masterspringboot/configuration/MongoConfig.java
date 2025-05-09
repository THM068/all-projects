package com.master.masterspringboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@PropertySource("classpath:config.properties")
@Configuration
public class MongoConfig {

    @Value("${mongodb.url}")
    private String dbURL;

    @Value(("${mongodb.db}"))
    private String dbName;

    @Autowired
    Environment env;

    @Bean
    public String dbName() {
        return String.format("%s-%s and  %s-%s", dbURL, dbName, env.getProperty("mysql.name", "dummy1"), env.getProperty("mysql.surname", "dummy2"));
    }


}
