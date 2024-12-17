package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Admin;
import com.example.demo.model.User;
import com.example.demo.service.AdminService;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController
public class UserController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private AdminService adminService; // DO We want this here in the class of USercontroller

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }
    
    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public Admin addAdmin(@RequestBody Admin admin) {
        return adminService.addAdmin(admin);
    }
    
    
    
}
