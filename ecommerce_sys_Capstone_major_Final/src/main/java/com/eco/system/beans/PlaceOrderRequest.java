package com.eco.system.beans;

import jakarta.validation.constraints.NotNull; // Importing @NotNull to ensure fields are not null
import jakarta.validation.constraints.Positive; // Importing @Positive to enforce that a number is positive
import lombok.AllArgsConstructor; // Lombok annotation to generate a constructor with all arguments
import lombok.Data; // Lombok annotation to generate getters, setters, toString, equals, and hashCode methods
import lombok.NoArgsConstructor; // Lombok annotation to generate a no-arguments constructor

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class PlaceOrderRequest {

    @NotNull(message = "CartItem ID is required") // Ensures that cartItemId cannot be null, with a custom error message if validation fails
    private Integer cartItemId;

    @Positive(message = "Quantity must be a positive number") // Ensures that quantity is a positive number
    @NotNull(message = "Quantity is required") // Ensures that quantity cannot be null, with a custom error message if validation fails
    private Integer quantity;
}
