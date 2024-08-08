package com.example.onlineshopping.service;

import com.example.onlineshopping.models.User;
import com.example.onlineshopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean authenticate(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String encodedPassword = user.getPassword();
            if (passwordEncoder.matches(password, encodedPassword)) {
                return true;
            }
        }

        return false;
    }
}

