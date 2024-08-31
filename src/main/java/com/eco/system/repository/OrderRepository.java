package com.eco.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eco.system.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    // You can add custom query methods if needed
}