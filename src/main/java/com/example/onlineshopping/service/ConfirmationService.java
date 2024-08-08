package com.example.onlineshopping.service;

import com.example.onlineshopping.models.User;

public interface ConfirmationService {
    String generateConfirmationToken(User user);
    boolean confirmUser(String token);

}
