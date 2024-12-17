package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.model.Admin;
import com.example.demo.model.User;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.UserRepository;



@Service
public class AdminService {
	
	@Autowired
    private AdminRepository adminRepository;
	
	
    public Admin addAdmin(@RequestBody Admin admin) {
        return adminRepository.save(admin);
    }
    
    
	public boolean validateAdmin(String adminUsername, String adminPassword) {
		// TODO Auto-generated method stub
		
		Optional<Admin> admincheck = adminRepository.findByUsername(adminUsername);
		
        System.out.println("Admin found: " + admincheck.isPresent());
        
        return admincheck.filter(admin -> admin.getPassword().equals(adminPassword)).isPresent();
	}

	
	

}
