package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.UserService;

@RestController
public class CustomerController {
    // Get user
    // Get users
    // Create user
    // Update user
    // Delete user
    
    @Autowired
    private UserService userService;

    @RequestMapping("/info")
    public String info(){
        return "The application is up...";
    }

}