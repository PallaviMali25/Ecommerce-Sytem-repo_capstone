package com.eco.system.beans;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemDto {
    
    @NotNull(message = "Product ID is mandatory")
    private Integer productId;

    @NotNull(message = "Customer ID is mandatory")
    private Integer customerId;
    

	public CartItemDto(@NotNull(message = "Product ID is mandatory") Integer productId,
			@NotNull(message = "Customer ID is mandatory") Integer customerId) {
		super();
		this.productId = productId;
		this.customerId = customerId;
	}
}

