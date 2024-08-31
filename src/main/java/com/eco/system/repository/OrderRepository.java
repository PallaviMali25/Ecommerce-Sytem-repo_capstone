package com.eco.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eco.system.entity.Order;

/**
 * Repository interface for performing CRUD operations on Order entities.
 * Extends JpaRepository to leverage built-in methods and custom queries.
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {

    // You can add custom query methods if needed
}
