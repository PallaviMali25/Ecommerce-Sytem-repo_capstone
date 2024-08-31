package com.eco.system.beans;

// Import for validation constraints
import jakarta.validation.constraints.NotNull; // Importing @NotNull to enforce non-null constraints on fields
import lombok.Data;

@Data // Lombok annotation to automatically generate getters, setters, toString, equals, and hashCode methods
public class CartItemUpdatebean {

    @NotNull(message = "Product ID is mandatory") // Ensures that productId cannot be null, with a custom error message if validation fails
    private Integer productId;

    /**
     * Constructor to initialize CartItemUpdatebean with a productId.
     * 
     * @param productId the ID of the product to be updated in the cart, must not be null
     */
    public CartItemUpdatebean(@NotNull(message = "Product ID is mandatory") Integer productId) {
        super(); // Calls the parent class constructor (implicitly Object class in this case)
        this.productId = productId; // Assigning the productId to the instance variable
    }
}
