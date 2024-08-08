package com.example.onlineshopping.service;

import com.example.onlineshopping.models.User;

public interface MailService {
    boolean verifyTokenAndActivateUser(String token);
    String sendMail(User user);


}
