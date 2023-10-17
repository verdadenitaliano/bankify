package com.example.bankify;

public class Model {

    String cardNumber, postalCode,password;

    public Model(String cardNumber, String postalCode, String password) {
        this.cardNumber = cardNumber;
        this.postalCode = postalCode;
        this.password = password;
    }

    public Model(){}

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
