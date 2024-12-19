package com.example.demo.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.persistence.Id;

@Document
public class CustomerCart {
    @Id
    private String cartId;
    @Field("customerId")
    private String customerId;
    @Field("items")
    private List<CustomerCartEntry> items;
    
    public String getCartId() {
        return cartId;
    }

    public List<CustomerCartEntry> getItems() {
        return items;
    }
    public void setItems(List<CustomerCartEntry> items) {
        this.items = items;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
