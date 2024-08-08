package com.example.onlineshopping.controller;

import com.example.onlineshopping.dto.ChangePasswordRequest;
import com.example.onlineshopping.dto.RegisterRequestDto;
import com.example.onlineshopping.models.Order;
import com.example.onlineshopping.models.Product;
import com.example.onlineshopping.models.User;
import com.example.onlineshopping.service.CategoryService;
import com.example.onlineshopping.service.OrderService;
import com.example.onlineshopping.service.ProductService;
import com.example.onlineshopping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final CategoryService categoryService;
    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;

    @PostMapping("/addcatagories")
    public ResponseEntity<String> addCategory(@RequestBody String categoryName) {
        categoryService.addCategory(categoryName);
        return ResponseEntity.status(HttpStatus.CREATED).body("Category added successfully.");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerAdmin(@RequestBody RegisterRequestDto request) {
        userService.registerAdmin(request.getUser(), request.getAddressDto());
        return ResponseEntity.status(HttpStatus.CREATED).body("Admin registered successfully.");
    }


    @GetMapping("/allorders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/pendingorders")
    public ResponseEntity<List<Order>> getAllPendingOrders() {
        List<Order> pendingOrders = orderService.getAllPendingOrders();
        return ResponseEntity.ok(pendingOrders);
    }

    @GetMapping("/allusers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/allproducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/{userId}/blockseller")
    public ResponseEntity<String> blockSeller(@PathVariable Long userId) {
        userService.blockSeller(userId);
        return ResponseEntity.ok("Seller blocked successfully");
    }

    @PostMapping("/{productId}/blockproduct")
    public ResponseEntity<String> blockProduct(@PathVariable Long productId) {
        productService.blockProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body("Product has been blocked successfully.");
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request, Principal connectUser) {
        userService.changePassword(request, connectUser);
        return ResponseEntity.ok("Password was changed successfully");
    }
}
