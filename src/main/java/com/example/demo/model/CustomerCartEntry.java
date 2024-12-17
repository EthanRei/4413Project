package com.example.demo.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CustomerCartEntry {
    private String customerId;
    private String itemId;
    private int qty;

    public CustomerCartEntry(String customerId, String itemId, int qty) {
        super();
        this.customerId = customerId;
        this.itemId = itemId;
        this.qty = qty;
    }

    // Getters and Setters

    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getItemId() {
        return itemId;
    }
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    public int getQty() {
        return qty;
    }
    public void setQty(int qty) {
        this.qty = qty;
    }
    

}