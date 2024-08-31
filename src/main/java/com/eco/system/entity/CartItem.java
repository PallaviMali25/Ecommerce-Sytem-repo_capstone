package com.eco.system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartItemId;

    @NotNull(message = "Product is mandatory")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull(message = "Customer is mandatory")
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    public CartItem(Integer cartItemId, @NotNull(message = "Customer is mandatory") Customer customer,
			@NotNull(message = "Product is mandatory") Product product) {
		super();
		this.cartItemId = cartItemId;
		this.product = product;
		this.customer = customer;
	}
}
