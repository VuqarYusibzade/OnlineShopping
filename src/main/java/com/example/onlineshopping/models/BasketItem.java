package com.example.onlineshopping.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "basket_id")
    @JsonIgnore
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    @Override
    public String toString() {
        return "BasketItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", basket=" + basket +
                ", product=" + product +
                ",quantity=" + quantity +
                '}';
    }

}
