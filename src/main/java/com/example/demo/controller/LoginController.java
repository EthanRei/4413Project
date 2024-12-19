package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api")
public class LoginController {
	
	
	@Autowired
	private UserService userservice; 
	@Autowired
	private AdminService adminservice;	
	
    @PostMapping("/im/signin")
    public ResponseEntity<?> checkUser(@RequestBody LoginRequest loginRequest) {
    	
    	
    	System.out.println("Received Username " + loginRequest.getUsername());
    	System.out.println("Received Password " + loginRequest.getPassword());

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
    
    @PostMapping("/im/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {    	
    	// existing user is showiung up as true when checking the database.        
        if (userservice.checkUserExists(user.getUsername(), user.getPassword())) {
            // If user exists already
            return ResponseEntity.status(400).body("{\"message\": \"User already exists\"}");
        }
        // Save the user to the database
        userservice.addUser(user);

        return ResponseEntity.ok("{\"message\": \"User registered successfully\"}");
        //return ResponseEntity.ok(user1); - this is for testing the endpoints that need the ID

    }

}