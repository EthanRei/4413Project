package com.example.demo.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.AdminRequest;
import com.example.demo.model.CustomerCart;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AdminService;
import com.example.demo.service.UserService;
import com.example.demo.service.CartService;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/cart")
public class CartController {
    
    @Autowired
	private UserRepository userRepository;
    @Autowired
    private CartService cartService;

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCustomerCart(@PathVariable("customerId") String customerId) {
        cartService.createCartForCustomer(customerId);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "worked");
        
        Optional<User> findUser = userRepository.findById(customerId);
        System.out.println("Present: " + findUser.isPresent());

        return ResponseEntity.ok().body(responseBody);    		
    }

    @PutMapping("/{customerId}/items")
    public ResponseEntity<?> updateCustomerCart(@PathVariable("customerId") String customerId, @RequestBody CustomerCart cart) {
        
        System.out.println(cart);
        System.out.println(cart.getItems());        
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "worked");
        
        Optional<User> findUser = userRepository.findById(customerId);
        System.out.println("Present: " + findUser.isPresent());

        return ResponseEntity.ok().body(responseBody);    		
    }
    

}
