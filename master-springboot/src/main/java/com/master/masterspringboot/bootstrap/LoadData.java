package com.master.masterspringboot.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LoadData implements CommandLineRunner  {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Loading data .....");
    }
}
