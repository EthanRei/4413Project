package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.AdminRequest;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.User;
import com.example.demo.service.AdminService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/login")
public class LoginController {
	
	
	@Autowired
	private UserService userservice; 
	@Autowired
	private AdminService adminservice;
	
	
    @PostMapping("/check")
    public ResponseEntity<?> checkUser(@RequestBody LoginRequest loginRequest) {
    	
    	boolean user_exists = userservice.checkUserExists(loginRequest.getUsername(), loginRequest.getPassword());
    	
    			
    	if (user_exists) {
    		// thats good, and the user can sign in 
            return ResponseEntity.ok().body("{\"message\": \"Success!\"}");    		
    	}
    	else {
            return ResponseEntity.status(401).body("{\"message\": \"Invalid credentials\"}");

    	}
    }
    
    @PostMapping("/admincheck")
    public ResponseEntity<?> checkAdmin(@RequestBody AdminRequest adminRequest) {
        System.out.println("Received Username: " + adminRequest.getUsername());
        System.out.println("Received Password: " + adminRequest.getPassword());

    	boolean admin_exists = adminservice.validateAdmin(adminRequest.getUsername(), adminRequest.getPassword());
    	if (admin_exists) {
    		// thats good, and the user can sign in 
            return ResponseEntity.ok().body("{\"message\": \"Admin exists\"}");    		
    	}
    	else {
            return ResponseEntity.status(401).body("{\"message\": \"Invalid credentials- ADMIN\"}");

    	}
    	
    	
    	
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Check if the user already exists
    	
    	// exisitng user is showiung up as true when checking the database.
        boolean existingUser = userservice.checkUserExists(user.getUsername(), user.getPassword());
        
        
        if (existingUser) {
            // If user exists already
            return ResponseEntity.status(400).body("{\"message\": \"User already exists\"}");
        }

        // Save the user to the database
        userservice.addUser(user);

        return ResponseEntity.ok("{\"message\": \"User registered successfully\"}");
    }

}
