package com.eco.system.beans;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class CartItemUpdatebean {

    @NotNull(message = "Product ID is mandatory")
    private Integer productId;
    public CartItemUpdatebean(@NotNull(message = "Product ID is mandatory") Integer productId) {
		super();
		this.productId = productId;
	}

}
