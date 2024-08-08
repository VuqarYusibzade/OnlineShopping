package com.example.onlineshopping.models;

import com.example.onlineshopping.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate orderDate;
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + (user != null ? user.getId() : null) +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", orderItems=" + orderItems +
                ", orderStatus=" + orderStatus +
                '}';
    }
}