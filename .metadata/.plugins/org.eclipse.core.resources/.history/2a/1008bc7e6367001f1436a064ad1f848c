package com.eco.system.beans;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    @NotBlank(message = "Product name is required")
    private String productName;

    @NotBlank(message = "Category is required")
    private String category;

    @Positive(message = "Price must be a positive number")
    @NotNull(message = "Price is required")  // Ensure price is not null
    private Integer price;

    @Positive(message = "Stock must be a positive number")
    @NotNull(message = "Stock is required")  // Ensure stock is not null
    private Integer stock;
}
