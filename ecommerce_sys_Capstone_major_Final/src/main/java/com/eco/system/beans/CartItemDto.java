package com.eco.system.beans;

// Import for validation constraints
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data // Lombok annotation to generate getters, setters, toString, equals, and hashCode methods
public class CartItemDto {

    @NotNull(message = "Product ID is mandatory") // Ensures that productId cannot be null, with a custom error message
    private Integer productId;

    @NotNull(message = "Customer ID is mandatory") // Ensures that customerId cannot be null, with a custom error message
    private Integer customerId;
    
   
    public CartItemDto(@NotNull(message = "Product ID is mandatory") Integer productId,
                       @NotNull(message = "Customer ID is mandatory") Integer customerId) {
        super(); // Calls the parent class constructor (Object class)
        this.productId = productId;
        this.customerId = customerId;
    }
}
