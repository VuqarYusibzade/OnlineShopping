package com.example.onlineshopping.service;

import com.example.onlineshopping.dto.ProductDto;
import com.example.onlineshopping.models.Product;

import java.util.List;

public interface ProductService {
    void addProduct(ProductDto productDto);

    void updateProduct(Long productId, ProductDto productDto, Long userId);

    void deleteProduct(Long productId, Long userId);

    List<Product> getProductsByUserId(Long userId);

    void addCategoryToProduct(Long productId, Long categoryId);

    List<ProductDto> getAllProductsForCustomer();

    List<Product> getAllProducts();

    void blockProduct(Long productId);
}
