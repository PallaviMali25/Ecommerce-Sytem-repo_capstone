package com.eco.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eco.system.entity.CartItem;

/**
 * Repository interface for performing CRUD operations on CartItem entities.
 * Extends JpaRepository to leverage built-in methods and custom queries.
 */
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    /**
     * Finds all CartItem entities associated with a specific customer.
     * 
     * @param customerId the ID of the customer
     * @return a list of CartItem entities for the given customer
     */
    List<CartItem> findByCustomerCustomerId(Integer customerId);

    /**
     * Deletes CartItem entities associated with a specific product.
     * 
     * @param productId the ID of the product
     */
    void deleteByProductProductId(Integer productId);

    /**
     * Deletes CartItem entities associated with a specific customer.
     * 
     * @param customerId the ID of the customer
     */
    void deleteByCustomerCustomerId(Integer customerId);
}
