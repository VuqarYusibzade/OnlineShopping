package com.example.onlineshopping.ServiceTest;

import com.example.onlineshopping.models.Basket;
import com.example.onlineshopping.models.BasketItem;
import com.example.onlineshopping.models.Product;
import com.example.onlineshopping.models.User;
import com.example.onlineshopping.repository.BasketItemRepository;
import com.example.onlineshopping.repository.BasketRepository;
import com.example.onlineshopping.repository.ProductRepository;
import com.example.onlineshopping.repository.UserRepository;
import com.example.onlineshopping.service.impl.BasketServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class BasketServiceTest {

    @Mock
    private BasketRepository basketRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BasketItemRepository basketItemRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BasketServiceImpl basketService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addToBasketTest() {
        Long userId = 1L;
        Long productId = 1L;
        User user = new User();
        Basket basket = new Basket();
        Product product = new Product();

        Mockito.when(basketRepository.findByUserId(userId)).thenReturn(Optional.of(basket));
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        basketService.addToBasket(userId, productId);

        Assertions.assertEquals(1, basket.getBasketItems().size());
        Assertions.assertEquals(product, basket.getBasketItems().get(0).getProduct());
    }

    @Test
    public void removeItemFromBasketTest() {
        Long basketItemId = 1L;
        Long userId = 1L;
        User user = new User();
        Basket basket = new Basket();
        BasketItem basketItem = new BasketItem();
        basketItem.setId(basketItemId);
        basket.getBasketItems().add(basketItem);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(basketRepository.findByUser(user)).thenReturn(basket);
        Mockito.when(basketItemRepository.findById(basketItemId)).thenReturn(Optional.of(basketItem));

        basketService.removeItemFromBasket(basketItemId, userId);

        Assertions.assertTrue(basket.getBasketItems().isEmpty());
    }

    @Test
    public void getBasketByUserIdTest() {
        Long userId = 1L;
        User user = new User();
        Basket basket = new Basket();

        Mockito.when(basketRepository.findByUserId(userId)).thenReturn(Optional.of(basket));

        Basket resultBasket = basketService.getBasketByUserId(userId);

        Assertions.assertEquals(basket, resultBasket);
    }

    @Test
    public void updateBasketItemQuantityTest() {
        Long userId = 1L;
        Long productId = 1L;
        int newQuantity = 5;
        User user = new User();
        Basket basket = new Basket();
        Product product = new Product();
        BasketItem basketItem = new BasketItem();
        basketItem.setProduct(product);
        basketItem.setQuantity(1);
        basket.getBasketItems().add(basketItem);

        Mockito.when(basketRepository.findByUserId(userId)).thenReturn(Optional.of(basket));

        basketService.updateBasketItemQuantity(userId, productId, newQuantity);

        Assertions.assertEquals(newQuantity, basketItem.getQuantity());
    }
}

