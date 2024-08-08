package com.example.onlineshopping.ServiceTest;

import com.example.onlineshopping.exceptions.CategoryAlreadyExistsException;
import com.example.onlineshopping.models.Category;
import com.example.onlineshopping.models.User;
import com.example.onlineshopping.repository.CategoryRepository;
import com.example.onlineshopping.repository.UserRepository;
import com.example.onlineshopping.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addCategoryTest() {
        String categoryName = "Test Category";
        Category category = new Category();
        category.setName(categoryName);

        Mockito.when(categoryRepository.findByName(categoryName)).thenReturn(Optional.empty());
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);

        categoryService.addCategory(categoryName);

        Mockito.verify(categoryRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void addCategoryAlreadyExistsTest() {
        String categoryName = "Test Category";
        Category category = new Category();
        category.setName(categoryName);

        Mockito.when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(category));

        Assertions.assertThrows(CategoryAlreadyExistsException.class, () -> {
            categoryService.addCategory(categoryName);
        });
    }

    @Test
    public void findByNameTest() {
        String name = "Test User";
        User user = new User();
        user.setName(name);

        Mockito.when(userRepository.findByName(name)).thenReturn(Optional.of(user));

        Optional<User> foundUser = categoryService.findByName(name);

        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals(name, foundUser.get().getName());
    }

    @Test
    public void findByNameNotFoundTest() {
        String name = "Test User";

        Mockito.when(userRepository.findByName(name)).thenReturn(Optional.empty());

        Optional<User> foundUser = categoryService.findByName(name);

        Assertions.assertTrue(foundUser.isEmpty());
    }
}

