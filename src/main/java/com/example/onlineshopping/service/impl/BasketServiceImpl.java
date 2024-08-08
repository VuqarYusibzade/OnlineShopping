package com.example.onlineshopping.service.impl;


import com.example.onlineshopping.exceptions.BasketNotFoundException;
import com.example.onlineshopping.exceptions.ProductNotFoundException;
import com.example.onlineshopping.models.Basket;
import com.example.onlineshopping.models.BasketItem;
import com.example.onlineshopping.models.Product;
import com.example.onlineshopping.models.User;
import com.example.onlineshopping.repository.BasketItemRepository;
import com.example.onlineshopping.repository.BasketRepository;
import com.example.onlineshopping.repository.ProductRepository;
import com.example.onlineshopping.repository.UserRepository;
import com.example.onlineshopping.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;
    private final BasketItemRepository basketItemRepository;
    private final UserRepository userRepository;

    @Override
    public void addToBasket(Long userId, Long productId) {
        Basket basket = basketRepository.findByUserId(userId)
                .orElseThrow(() -> new BasketNotFoundException("Basket not found for user with ID: " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        BasketItem basketItem = BasketItem.builder()
                .basket(basket)
                .product(product)
                .quantity(1)
                .build();

        basket.getBasketItems().add(basketItem);
        basketRepository.save(basket);
    }


    @Override
    @Transactional
    public void removeItemFromBasket(Long basketItemId, Long userId) {

        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            Basket basket = basketRepository.findByUser(user);

            Optional<BasketItem> basketItemOptional = basketItemRepository.findById(basketItemId);

            basketItemOptional.ifPresent(basketItem -> {
                basket.getBasketItems().remove(basketItem);
            });


            basketRepository.save(basket);
        }
    }

    @Override
    public Basket getBasketByUserId(Long userId) {
        return basketRepository.findByUserId(userId)
                .orElseThrow(() -> new BasketNotFoundException("Basket not found"));
    }

    @Override
    public void updateBasketItemQuantity(Long userId, Long productId, int newQuantity) {
        Basket basket = basketRepository.findByUserId(userId).orElseThrow(() -> new BasketNotFoundException("Basket not found for user: " + userId));

        Optional<BasketItem> optionalItem = basket.getBasketItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId)).findFirst();
        optionalItem.ifPresent(item -> item.setQuantity(newQuantity));
        basketRepository.save(basket);
    }

}
