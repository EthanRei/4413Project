package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CreditCard {
    @Id
    private String creditCardId;
    private String cardNumber;
    private String expiry;
    private String cvv;

    public CreditCard(String creditCardId, String cardNumber, String expiry, String cvv) {
        super();
        this.creditCardId = creditCardId;
        this.cardNumber = cardNumber;
        this.expiry = expiry;
        this.cvv = cvv;
    }

    public String getCreditCardId() {
        return creditCardId;
    }
    public void setCreditCardId(String creditCardId) {
        this.creditCardId = creditCardId;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public String getExpiry() {
        return expiry;
    }
    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }
    public String getCvv() {
        return cvv;
    }
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    
}