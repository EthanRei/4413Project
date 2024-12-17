package com.example.demo.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document
public class OrderDetails {
    @Id
    private String orderId;
    private String customerId;
    private int total;
    private String date;

    public OrderDetails(String orderId, String customerId, int total, String date) {
        super();
        this.orderId = orderId;
        this.customerId = customerId;
        this.total = total;
        this.date = date;
    }

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    
    
}