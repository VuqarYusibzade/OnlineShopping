package com.example.onlineshopping.ServiceTest;

import com.example.onlineshopping.dto.ProductDto;
import com.example.onlineshopping.models.Product;
import com.example.onlineshopping.models.User;
import com.example.onlineshopping.repository.ProductRepository;
import com.example.onlineshopping.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;


    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addProductTest() {
        ProductDto productDto = new ProductDto();
        productDto.setName("Test Product");
        productDto.setDescription("Test Description");
        productDto.setPrice(BigDecimal.valueOf(100));
        productDto.setQuantity(10);
        productDto.setImageUrl("test-url");
        productDto.setUser(null);

        productService.addProduct(productDto);

        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void updateProductTest() {
        Long productId = 1L;
        Long userId = 1L;
        ProductDto productDto = new ProductDto();
        productDto.setName("Updated Test Product");
        productDto.setDescription("Updated Test Description");
        productDto.setPrice(BigDecimal.valueOf(150));
        productDto.setQuantity(15);
        productDto.setImageUrl("updated-test-url");

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setUser(new User());

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        productService.updateProduct(productId, productDto, userId);

        Assertions.assertEquals(productDto.getName(), existingProduct.getName());
        Assertions.assertEquals(productDto.getDescription(), existingProduct.getDescription());
        Assertions.assertEquals(productDto.getPrice(), existingProduct.getPrice());
        Assertions.assertEquals(productDto.getQuantity(), existingProduct.getQuantity());
        Assertions.assertEquals(productDto.getImageUrl(), existingProduct.getImageUrl());
        Mockito.verify(productRepository, Mockito.times(1)).save(existingProduct);
    }

}

