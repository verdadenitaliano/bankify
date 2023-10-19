package com.example.bankify;

public class Model {

    String cardNumber, customerName, customerDoB, customerGender,customerAddress, customerPostalCode,phone,email;

    public Model(String cardNumber, String customerName, String customerDoB, String customerGender,
                 String customerAddress,String customerPostalCode,String phone,String email ) {
        this.cardNumber = cardNumber;
        this.customerName = customerName;
        this.customerDoB = customerDoB;
        this.customerGender = customerGender;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.phone = phone;
        this.email = email;
    }

    public Model(){}

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerDoB() {
        return customerDoB;
    }

    public void setCustomerDoB(String customerDoB) {
        this.customerDoB = customerDoB;
    }

    public String getCustomerGender() {
        return customerGender;
    }

    public void setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
    }

    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
