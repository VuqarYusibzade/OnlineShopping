package com.example.onlineshopping.repository;

import com.example.onlineshopping.enums.OrderStatus;
import com.example.onlineshopping.enums.Role;
import com.example.onlineshopping.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByOrderStatus(OrderStatus status);

    List<Order> findByUserRoleAndOrderStatus(Role role, OrderStatus orderStatus);
}
