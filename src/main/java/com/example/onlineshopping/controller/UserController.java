package com.example.onlineshopping.controller;

import com.example.onlineshopping.dto.*;
import com.example.onlineshopping.models.Order;
import com.example.onlineshopping.models.User;
import com.example.onlineshopping.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final ProductService productService;
    private final BasketService basketService;
    private final OrderService orderService;
    private final AuthService authService;


    @GetMapping("/allproduct")
    public ResponseEntity<List<ProductDto>> getAllProductsForCustomer() {
        List<ProductDto> products = productService.getAllProductsForCustomer();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Boolean> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        String email = authenticationRequest.getEmail();
        String password = authenticationRequest.getPassword();

        boolean isAuthenticated = authService.authenticate(email, password);
        return ResponseEntity.ok(isAuthenticated);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequestDto request) {
        userService.registerUser(request.getUserDto(), request.getAddressDto());
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
    }


    @GetMapping("/allusers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/{userId}/products")
    public ResponseEntity<String> viewProducts(@PathVariable Long userId) {
        return ResponseEntity.ok("Viewed products successfully.");
    }

    @PostMapping("/{userId}/basket/add")
    public ResponseEntity<String> addToBasket(@PathVariable Long userId, @RequestParam Long productId) {
        basketService.addToBasket(userId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product added to basket successfully.");
    }


    @DeleteMapping("/{basketItemId}/{userId}")
    public ResponseEntity<String> removeItemFromBasket1(@PathVariable Long basketItemId, @PathVariable Long userId) {
        try {
            basketService.removeItemFromBasket(basketItemId, userId);
            return ResponseEntity.ok("Item removed from basket successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while removing item from basket.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();


        if (userService.authenticateUser(email, password)) {
            return ResponseEntity.ok("LoginSuccessfull");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("wrong email or password.");
        }
    }

    @PutMapping("/{userId}/basket/{productId}/updateQuantity/{newQuantity}")
    public ResponseEntity<String> updateBasketItemQuantity(@PathVariable Long userId, @PathVariable Long productId, @PathVariable int newQuantity) {
        basketService.updateBasketItemQuantity(userId, productId, newQuantity);
        return ResponseEntity.ok("Basket item quantity updated successfully.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @PostMapping("/place/{userId}")
    public ResponseEntity<String> placeOrder(@PathVariable Long userId) {
        Order order = orderService.placeOrder(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order placed successfully. Order ID: " + order.getId());
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request, Principal connectUser) {
        userService.changePassword(request, connectUser);
        return ResponseEntity.ok("Password was changed successfully");
    }


}

