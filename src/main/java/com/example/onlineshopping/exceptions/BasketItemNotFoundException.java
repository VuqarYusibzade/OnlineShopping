package com.example.onlineshopping.exceptions;

public class BasketItemNotFoundException extends RuntimeException {
    public BasketItemNotFoundException(String message) {
        super(message);
    }
}
