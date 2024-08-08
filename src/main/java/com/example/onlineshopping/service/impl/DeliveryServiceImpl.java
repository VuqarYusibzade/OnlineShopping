package com.example.onlineshopping.service.impl;

import com.example.onlineshopping.enums.OrderStatus;
import com.example.onlineshopping.models.Delivery;
import com.example.onlineshopping.models.Order;
import com.example.onlineshopping.repository.DeliveryRepository;
import com.example.onlineshopping.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;

    @Override
    public void deliverOrder(Order order) {
        Delivery delivery = Delivery.builder()
                .order(order)
                .orderStatus(OrderStatus.IT_IS_IN_CARGO)
                .build();


        deliveryRepository.save(delivery);
    }
}
