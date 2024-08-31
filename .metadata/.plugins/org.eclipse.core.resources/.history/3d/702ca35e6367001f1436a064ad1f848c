package com.eco.system.beans;

import java.util.List;

import com.eco.system.entity.Order;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")  // Ensure email is not blank
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    @NotBlank(message = "Password is required")  // Ensure password is not blank
    private String password;

    @NotBlank(message = "Address is required")  // Ensure address is not blank
    private String address;
    
//    private List<Order> orders;
}
