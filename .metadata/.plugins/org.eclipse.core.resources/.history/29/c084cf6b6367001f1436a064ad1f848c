package com.eco.system.beans;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderRequest {

    @NotNull(message = "CartItem ID is required")
    private Integer cartItemId;

    @Positive(message = "Quantity must be a positive number")
    @NotNull(message = "Quantity is required")  // Ensure quantity is not null
    private Integer quantity;
}
