package com.eco.system.beans;

import jakarta.validation.constraints.NotBlank; // Importing @NotBlank to ensure fields are not empty
import jakarta.validation.constraints.Positive; // Importing @Positive to enforce that a number is positive
import jakarta.validation.constraints.NotNull; // Importing @NotNull to ensure fields are not null
import lombok.AllArgsConstructor; // Lombok annotation to generate a constructor with all arguments
import lombok.Data; // Lombok annotation to generate getters, setters, toString, equals, and hashCode methods
import lombok.NoArgsConstructor; // Lombok annotation to generate a no-arguments constructor

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class ProductDTO {

    @NotBlank(message = "Product name is required") // Ensures that productName cannot be blank, with a custom error message if validation fails
    private String productName;

    @NotBlank(message = "Category is required") // Ensures that category cannot be blank, with a custom error message if validation fails
    private String category;

    @Positive(message = "Price must be a positive number") // Ensures that price is a positive number
    @NotNull(message = "Price is required") // Ensures that price cannot be null, with a custom error message if validation fails
    private Integer price;

    @Positive(message = "Stock must be a positive number") // Ensures that stock is a positive number
    @NotNull(message = "Stock is required") // Ensures that stock cannot be null, with a custom error message if validation fails
    private Integer stock;
}
