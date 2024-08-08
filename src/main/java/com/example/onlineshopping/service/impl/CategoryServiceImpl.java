package com.example.onlineshopping.service.impl;

import com.example.onlineshopping.exceptions.CategoryAlreadyExistsException;
import com.example.onlineshopping.models.Category;
import com.example.onlineshopping.models.User;
import com.example.onlineshopping.repository.CategoryRepository;
import com.example.onlineshopping.repository.UserRepository;
import com.example.onlineshopping.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public void addCategory(String categoryName) {
        Category category = new Category();
        category.setName(categoryName);

        categoryRepository.findByName(categoryName).ifPresent(c -> {
            throw new CategoryAlreadyExistsException("This category already exists");
        });

        categoryRepository.save(category);
    }

    @Override
    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

}
