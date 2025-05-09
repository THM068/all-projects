package com.master.masterspringboot.controllers;

import com.master.masterspringboot.database.entity.User;
import com.master.masterspringboot.database.entity.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/create")
    public User createUser(){
        return userRepository.save(new User("thando.mafela", "12321342", true));
    }
}
