package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Item {
    @Id
    private String itemId;
    private String itemImagePath;
    private int qty;

    public Item(String itemId, String itemImagePath, int qty) {
        super();
        this.itemId = itemId;
        this.itemImagePath = itemImagePath;
        this.qty = qty;
    }

    public String getItemId() {
        return itemId;
    }
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    public String getItemImagePath() {
        return itemImagePath;
    }
    public void setItemImagePath(String itemImagePath) {
        this.itemImagePath = itemImagePath;
    }
    public int getQty() {
        return qty;
    }
    public void setQty(int qty) {
        this.qty = qty;
    }

    
}