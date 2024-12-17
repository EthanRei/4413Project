package com.example.demo.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class OrderEntry {
    private String orderId;
    private String itemId;
    private int qty;

    public OrderEntry(String orderId, String itemId, int qty) {
        super();
        this.orderId = orderId;
        this.itemId = itemId;
        this.qty = qty;
    }

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
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