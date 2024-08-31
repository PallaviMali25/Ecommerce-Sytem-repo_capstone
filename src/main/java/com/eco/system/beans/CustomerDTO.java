package com.eco.system.beans;

import java.util.List;

import com.eco.system.entity.Order; // Importing Order entity for potential usage in future extensions

import jakarta.validation.constraints.Email; // Importing @Email for email validation
import jakarta.validation.constraints.NotBlank; // Importing @NotBlank to ensure fields are not empty
import jakarta.validation.constraints.Size; // Importing @Size to enforce size constraints on strings
import lombok.AllArgsConstructor; // Lombok annotation to generate a constructor with all arguments
import lombok.Data; // Lombok annotation to generate getters, setters, toString, equals, and hashCode methods
import lombok.NoArgsConstructor; // Lombok annotation to generate a no-arguments constructor

@Data // Lombok annotation to automatically generate boilerplate code like getters, setters, toString, equals, and hashCode methods
@AllArgsConstructor // Lombok annotation to generate a constructor with all fields as parameters
@NoArgsConstructor // Lombok annotation to generate a no-arguments constructor
public class CustomerDTO {

    @NotBlank(message = "Name is required") // Ensures that the name cannot be blank, with a custom error message if validation fails
    private String name;

    @Email(message = "Email should be valid") // Ensures that the email field contains a valid email address format
    @NotBlank(message = "Email is required") // Ensures that the email cannot be blank, with a custom error message if validation fails
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters") // Enforces a minimum length of 6 characters for the password
    @NotBlank(message = "Password is required") // Ensures that the password cannot be blank, with a custom error message if validation fails
    private String password;

    @NotBlank(message = "Address is required") // Ensures that the address cannot be blank, with a custom error message if validation fails
    private String address;
    
//    private List<Order> orders; // Field to hold the customer's orders, commented out for now (uncomment if needed in future implementations)
}
