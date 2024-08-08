package com.example.onlineshopping.dto;

import com.example.onlineshopping.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    private String imageUrl;
    private User user;
}

