package com.eco.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eco.system.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByCustomerCustomerId(Integer customerId);

	void deleteByProductProductId(Integer productId);
	void deleteByCustomerCustomerId(Integer customerId);
}
