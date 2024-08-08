package com.example.onlineshopping.service;

import com.example.onlineshopping.models.Token;
import com.example.onlineshopping.models.User;
import com.example.onlineshopping.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final UserService userService;

    public void saveTokenToDatabase(String token, String email) {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            Token tokenEntity = new Token();
            tokenEntity.setToken(token);
            tokenEntity.setUser(user);
            tokenRepository.save(tokenEntity);
        } else {
            throw new IllegalArgumentException("User not found with email: " + email);
        }
    }


}

