package com.example.onlineshopping.controller;

import com.example.onlineshopping.dto.ChangePasswordRequest;
import com.example.onlineshopping.dto.ProductDto;
import com.example.onlineshopping.dto.RegisterRequestDto;
import com.example.onlineshopping.models.Order;
import com.example.onlineshopping.models.Product;
import com.example.onlineshopping.service.DeliveryService;
import com.example.onlineshopping.service.ProductService;
import com.example.onlineshopping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/seller")
@RequiredArgsConstructor
public class SellerController {

    private final ProductService productService;
    private final UserService userService;
    private final DeliveryService deliveryService;

    @PostMapping("/products")
    public void addProduct(@RequestBody ProductDto productDto) {
        productService.addProduct(productDto);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerSeller(@RequestBody RegisterRequestDto request) {
        userService.registerSeller(request.getUserDto(), request.getAddressDto());
        return ResponseEntity.status(HttpStatus.CREATED).body("Seller registered successfully.");
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Long productId, @RequestBody ProductDto productDto, @RequestParam Long userId) {
        productService.updateProduct(productId, productDto, userId);
        return ResponseEntity.ok("Product updated successfully");
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request, Principal connectUser) {
        userService.changePassword(request, connectUser);
        return ResponseEntity.ok("Password was changed successfully");
    }

    @DeleteMapping("/products/{productId}")
    public void deleteProduct(@PathVariable Long productId, @RequestParam Long userId) {
        productService.deleteProduct(productId, userId);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProductsBySellerId(@RequestParam Long sellerId) {
        List<Product> products = productService.getProductsByUserId(sellerId);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/{productId}/categories/{categoryId}")
    public ResponseEntity<String> addCategoryToProduct(@PathVariable Long productId, @PathVariable Long categoryId) {
        productService.addCategoryToProduct(productId, categoryId);
        return ResponseEntity.ok("Category added to product successfully");
    }

    @PostMapping("/deliver")
    public ResponseEntity<String> deliverOrder(@RequestBody Order order) {
        deliveryService.deliverOrder(order);
        return ResponseEntity.ok("Order delivered successfully");
    }
}
