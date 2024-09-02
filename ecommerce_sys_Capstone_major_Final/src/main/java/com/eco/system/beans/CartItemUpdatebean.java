package com.eco.system.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// Import for validation constraints
import jakarta.validation.constraints.NotNull; // Importing @NotNull to enforce non-null constraints on fields
import lombok.Data;

@Data 
public class CartItemUpdatebean {

    @NotNull(message = "Product ID is mandatory") // Ensures that productId cannot be null, with a custom error message if validation fails
    private Integer productId;
    
    @JsonCreator
    public CartItemUpdatebean(@JsonProperty("productId") Integer productId) {
        this.productId = productId;
    }


}
