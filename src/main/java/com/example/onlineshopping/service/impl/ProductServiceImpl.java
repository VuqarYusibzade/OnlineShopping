package com.example.onlineshopping.service.impl;

import com.example.onlineshopping.dto.ProductDto;
import com.example.onlineshopping.enums.ProductStatus;
import com.example.onlineshopping.enums.ProductType;
import com.example.onlineshopping.models.Category;
import com.example.onlineshopping.models.Product;
import com.example.onlineshopping.repository.CategoryRepository;
import com.example.onlineshopping.repository.ProductRepository;
import com.example.onlineshopping.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void addProduct(ProductDto productDto) {
        Product product = Product.builder().name(productDto.getName()).description(productDto.getDescription())
                .price(productDto.getPrice()).quantity(productDto.getQuantity()).imageUrl(productDto.getImageUrl())
                .user(productDto.getUser()).productType(ProductType.Physical).productStatus(ProductStatus.IN_STOCK).build();
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Long productId, ProductDto productDto, Long userId) {
        productRepository.findById(productId).ifPresent(existingProduct -> {
            if (existingProduct.getUser().getId().equals(userId)) {
                existingProduct.setName(productDto.getName());
                existingProduct.setDescription(productDto.getDescription());
                existingProduct.setPrice(productDto.getPrice());
                existingProduct.setQuantity(productDto.getQuantity());
                existingProduct.setImageUrl(productDto.getImageUrl());
                productRepository.save(existingProduct);
            }
        });
    }


    @Override
    public void deleteProduct(Long productId, Long userId) {
        productRepository.findById(productId)
                .filter(existingProduct -> existingProduct.getUser().getId().equals(userId))
                .ifPresent(productRepository::delete);
    }

    @Override
    public List<Product> getProductsByUserId(Long userId) {
        return productRepository.findByUserId(userId);
    }

    @Override
    public List<ProductDto> getAllProductsForCustomer() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductDto).collect(Collectors.toList());
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    private ProductDto mapToProductDto(Product product) {
        return ProductDto.builder().name(product.getName()).description(product.getDescription())
                .price(product.getPrice()).quantity(product.getQuantity()).imageUrl(product.getImageUrl())
                .build();
    }

    public void blockProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        optionalProduct.ifPresent(product -> {
            product.setProductStatus(ProductStatus.BLOCKED);
            productRepository.save(product);
        });
    }

    @Override
    public void addCategoryToProduct(Long productId, Long categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));

        product.getCategories().add(category);
        productRepository.save(product);
    }

}
