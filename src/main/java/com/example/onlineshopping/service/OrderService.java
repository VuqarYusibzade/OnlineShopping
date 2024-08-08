package com.example.onlineshopping.service;

import com.example.onlineshopping.models.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(Long userId);

    List<Order> getAllOrders();

    List<Order> getAllPendingOrders();

    List<Order> getPendingOrdersForSeller(Long sellerId);

    List<Order> getOrdersBySellerId(Long sellerId);
}
