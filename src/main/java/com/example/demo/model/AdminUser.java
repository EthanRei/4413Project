package com.example.demo.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document
public class AdminUser {
    @Id
    private String adminId;
    private String username;
    private String password;
    private String email;
    
    public AdminUser(String adminId, String username, String password, String email) {
        super();
        this.adminId = adminId;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getters and Setters
    
    public String getAdminId() {
        return adminId;
    }


    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

}
