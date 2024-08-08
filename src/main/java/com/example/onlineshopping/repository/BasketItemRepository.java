package com.example.onlineshopping.repository;

import com.example.onlineshopping.models.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {
    void deleteById(Long basketItemId);

}
