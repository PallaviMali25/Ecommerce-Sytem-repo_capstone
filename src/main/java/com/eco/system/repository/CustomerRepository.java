package com.eco.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eco.system.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	Customer findByEmail(String username);

    
}
