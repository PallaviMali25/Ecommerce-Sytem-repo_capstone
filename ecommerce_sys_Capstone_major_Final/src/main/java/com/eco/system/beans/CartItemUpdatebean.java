package com.eco.system.beans;

// Import for validation constraints
import jakarta.validation.constraints.NotNull; // Importing @NotNull to enforce non-null constraints on fields
import lombok.Data;

@Data 
public class CartItemUpdatebean {

    @NotNull(message = "Product ID is mandatory") // Ensures that productId cannot be null, with a custom error message if validation fails
    private Integer productId;

    public CartItemUpdatebean(@NotNull(message = "Product ID is mandatory") Integer productId) {
        super(); // Calls the parent class constructor ( Object class)
        this.productId = productId; // Assigning the productId to the instance variable
    }
}
