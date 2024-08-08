package com.example.onlineshopping.service.impl;

import com.example.onlineshopping.enums.OrderStatus;
import com.example.onlineshopping.enums.Role;
import com.example.onlineshopping.exceptions.InsufficientQuantityException;
import com.example.onlineshopping.models.*;
import com.example.onlineshopping.repository.*;
import com.example.onlineshopping.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final BasketRepository basketRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final BasketItemRepository basketItemRepository;


    @Override
    public Order placeOrder(Long userId) {
        Basket basket = basketRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Basket not found for user: " + userId));
        List<OrderItem> orderItems = createOrderItems(basket.getBasketItems());
        BigDecimal totalAmount = orderItems.stream()
                .map(orderItem -> orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .user(User.builder().id(userId).build()).orderDate(LocalDate.now())
                .totalAmount(totalAmount).orderItems(orderItems).orderStatus(OrderStatus.IT_IS_IN_CARGO).build();

        Order finalOrder = order;
        orderItems.forEach(orderItem -> orderItem.setOrder(finalOrder));

        order = orderRepository.save(order);

        orderItems.forEach(orderItem -> {
            Product product = orderItem.getProduct();
            int quantity = product.getQuantity();
            int orderedQuantity = orderItem.getQuantity();

            if (quantity < orderedQuantity) {
                throw new InsufficientQuantityException("Not enough quantity for product: " + product.getName());
            }

            product.setQuantity(quantity - orderedQuantity);
            productRepository.save(product);
        });

        basket.getBasketItems().forEach(basketItem -> basketItemRepository.delete(basketItem));

        return order;
    }


    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


    @Override
    public List<Order> getAllPendingOrders() {
        return orderRepository.findByOrderStatus(OrderStatus.PENDING);
    }

    @Override
    public List<Order> getPendingOrdersForSeller(Long sellerId) {
        return userRepository.findById(sellerId)
                .filter(user -> user.getRole() == Role.SELLER)
                .map(user -> orderRepository.findByUserRoleAndOrderStatus(Role.SELLER, OrderStatus.PENDING))
                .orElse(Collections.emptyList());
    }


    @Override
    public List<Order> getOrdersBySellerId(Long sellerId) {
        return null;
    }


    private List<OrderItem> createOrderItems(List<BasketItem> basketItems) {
        return basketItems.stream()
                .map(basketItem -> OrderItem.builder().product(basketItem.getProduct())
                        .quantity(basketItem.getQuantity()).unitPrice(basketItem.getProduct().getPrice()).quantity(basketItem.getQuantity()).unitPrice(basketItem.getProduct().getPrice())
                        .build()).collect(Collectors.toList());
    }

}
