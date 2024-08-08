package com.example.onlineshopping.repository;

import com.example.onlineshopping.models.Basket;
import com.example.onlineshopping.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    Optional<Basket> findByUserId(Long userId);

    Basket findByUser(User user);
}
