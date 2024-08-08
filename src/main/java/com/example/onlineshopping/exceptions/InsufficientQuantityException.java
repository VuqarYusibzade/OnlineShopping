package com.example.onlineshopping.exceptions;

public class InsufficientQuantityException extends RuntimeException {
    public InsufficientQuantityException(String message) {
        super(message);
    }
}

