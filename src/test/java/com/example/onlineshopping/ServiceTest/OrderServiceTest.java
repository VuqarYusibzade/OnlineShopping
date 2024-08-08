package com.example.onlineshopping.ServiceTest;

import com.example.onlineshopping.enums.OrderStatus;
import com.example.onlineshopping.exceptions.InsufficientQuantityException;
import com.example.onlineshopping.models.*;
import com.example.onlineshopping.repository.BasketItemRepository;
import com.example.onlineshopping.repository.BasketRepository;
import com.example.onlineshopping.repository.OrderRepository;
import com.example.onlineshopping.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceTest {

    @Mock
    private BasketRepository basketRepository;

    @Mock
    private OrderRepository orderRepository;


    @Mock
    private BasketItemRepository basketItemRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void placeOrderTest() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        Basket basket = new Basket();
        basket.setUser(user);
        List<BasketItem> basketItems = new ArrayList<>();
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(100));
        product.setQuantity(10);
        BasketItem basketItem = new BasketItem();
        basketItem.setProduct(product);
        basketItem.setQuantity(2);
        basketItems.add(basketItem);
        basket.setBasketItems(basketItems);

        Mockito.when(basketRepository.findByUserId(userId)).thenReturn(java.util.Optional.of(basket));
        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(new Order());

        Order order = orderService.placeOrder(userId);

        Assertions.assertNotNull(order);
        Assertions.assertEquals(OrderStatus.IT_IS_IN_CARGO, order.getOrderStatus());
        Mockito.verify(orderRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(basketItemRepository, Mockito.times(1)).deleteAll(basketItems);
    }

    @Test
    public void placeOrderInsufficientQuantityTest() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        Basket basket = new Basket();
        basket.setUser(user);
        List<BasketItem> basketItems = new ArrayList<>();
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(100));
        product.setQuantity(1);
        BasketItem basketItem = new BasketItem();
        basketItem.setProduct(product);
        basketItem.setQuantity(2);
        basketItems.add(basketItem);
        basket.setBasketItems(basketItems);

        Mockito.when(basketRepository.findByUserId(userId)).thenReturn(java.util.Optional.of(basket));

        Assertions.assertThrows(InsufficientQuantityException.class, () -> {
            orderService.placeOrder(userId);
        });
        Mockito.verify(orderRepository, Mockito.never()).save(Mockito.any());
        Mockito.verify(basketItemRepository, Mockito.never()).deleteAll(Mockito.any());
    }
}

