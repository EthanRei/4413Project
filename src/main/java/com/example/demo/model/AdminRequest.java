package com.example.demo.model;

public class AdminRequest {
    private String username;  // Match "username" in JSON
    private String password;  // Match "password" in JSON

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}