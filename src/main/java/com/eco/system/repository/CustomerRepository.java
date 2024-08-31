package com.eco.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eco.system.entity.Customer;

/**
 * Repository interface for performing CRUD operations on Customer entities.
 * Extends JpaRepository to leverage built-in methods and custom queries.
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    /**
     * Finds a Customer entity by its email address.
     * 
     * @param username the email address of the customer
     * @return the Customer entity with the given email address
     */
    Customer findByEmail(String username);

}
