package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
     @Autowired
     private UserRepository userRepository;

     public User addUser(@RequestBody User user) {
        return userRepository.save(user);
     }

	public boolean checkUserExists(String username, String password) {
		// TODO Auto-generated method stub
		
		
		
		Optional<User> userCheck = userRepository.findByUsername(username);
		
        return userCheck.filter(user -> user.getPassword().equals(password)).isPresent();

	}
	
}
