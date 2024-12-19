package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartService cartService;

    public User registerNewUser(User user) {
        User savedUser = userRepository.save(user);
        savedUser.getUserId();
        cartService.createCartForCustomer(savedUser.getUserId());
        return savedUser;
    }

	public boolean checkValidUser(String username, String password) {		
	    Optional<User> userCheck = userRepository.findByUsername(username);
        return userCheck.filter(user -> user.getPassword().equals(password)).isPresent();
	}

    public boolean checkUsernameTaken(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public User getCustomerByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) { return user; }

        // No user found
        throw new UserNotFoundException();
    }

    public User getCustomerById(String customerID) throws UserNotFoundException {
        // Fetch the customer from the database by ID
        User user = userRepository.findById(customerID).orElse(null);
        if (user != null) { return user; }

        // No user found
        throw new UserNotFoundException();
    }

	public List<User> getUsers() {
	    return userRepository.findAll();

	}
	public User updateUser(User user) {
		
		return userRepository.save(user);
	}
	
}