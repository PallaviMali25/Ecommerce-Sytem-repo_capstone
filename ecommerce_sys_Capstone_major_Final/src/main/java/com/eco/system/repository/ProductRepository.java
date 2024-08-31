package com.eco.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eco.system.entity.Product;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Custom query methods can be added if necessary
}
