package com.example.onlineshopping.service;

import com.example.onlineshopping.models.Basket;


public interface BasketService {
    void addToBasket(Long userId, Long productId);
    void removeItemFromBasket(Long basketItemId, Long userId);
    Basket getBasketByUserId(Long userId);
    void updateBasketItemQuantity(Long userId, Long productId, int newQuantity);
}
