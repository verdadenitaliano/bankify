package com.example.bankify;

public class User {

        private String cardNumber;
        private String password;
        public User() {}

        public User(String cardNumber, String password) {
            this.cardNumber = cardNumber;
            this.password = password;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }


}
