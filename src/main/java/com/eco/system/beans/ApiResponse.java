package com.eco.system.beans;

// Lombok annotations to reduce boilerplate code
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields as arguments
public class ApiResponse {

    // The status message of the response (e.g., "Success", "Error")
    private String status;
    
    // The HTTP status code associated with the response (e.g., 200, 404)
    private Integer code;
    
}
