package com.example.onlineshopping.service;

import com.example.onlineshopping.models.User;

import java.util.Optional;

public interface CategoryService {
    void addCategory(String categoryName);

    Optional<User> findByName(String name);
}
