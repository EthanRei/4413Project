package com.example.demo.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Admin;
import com.example.demo.model.User;
import com.example.demo.service.AdminService;
import com.example.demo.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/im")
public class UserController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private AdminService adminService; // DO We want this here in the class of USercontroller

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User addUser(@RequestBody User user) {
        return userService.registerNewUser(user);
    }
    
    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public Admin addAdmin(@RequestBody Admin admin) {
        return adminService.addAdmin(admin);
    }
    
    // GET /api/im/info/{CustomerID}
    
    
    @GetMapping("/info/{customerID}")
    public ResponseEntity<?> getCustomerInfo(@PathVariable String customerID) {
    	// Fetch the customer from the ID GIVEN to us
        User customer = userService.getCustomerById(customerID);
        
        if (customer != null) {
        	return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.status(404).body("{\"message\": \"Customer not found\"}");
        }
    }
    
    
    // TODO THIS MAYBE HAS TO CHANGE
    @PutMapping("/info/{customerID}")
    public ResponseEntity<?> updateCustomerInfo(@PathVariable String customerID, @RequestBody User updatedUser) {

    	User existingUser = userService.getCustomerById(customerID);

        if (existingUser == null) {
            return ResponseEntity.status(404).body("{\"message\": \"Customer not found\"}");
        }

       
        
        // CHECKING if any fields need an update.
        if (updatedUser.getUsername() != null) {
        	existingUser.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getPassword() != null) {
        	existingUser.setPassword(updatedUser.getPassword());
        }
        if (updatedUser.getFirstName() != null) {
        	existingUser.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null) {
        	existingUser.setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getEmail() != null) {
        	existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getCreditCardCVV() != null) {
        	existingUser.setCreditCardCVV(updatedUser.getCreditCardCVV());
        }
        if (updatedUser.getCreditCardNumber() != null) {
        	existingUser.setCreditCardNumber(updatedUser.getCreditCardNumber());
        }
        if (updatedUser.getAddress() != null) {
        	existingUser.setAddress(updatedUser.getAddress());
        }

        User savedUser = userService.updateUser(existingUser);

        return ResponseEntity.ok(savedUser);
    }
    
    
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
    	
        List<User> users = userService.getUsers();

        if (users.isEmpty()) {
            return ResponseEntity.status(404).body("{\"message\": \"No users found\"}");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Users retrieved successfully");
        response.put("users", users);

        return ResponseEntity.ok(response);
    }
    
    
    
    
}