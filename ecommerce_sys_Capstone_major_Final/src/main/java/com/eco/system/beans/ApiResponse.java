package com.eco.system.beans;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class ApiResponse {

    // The status message of the response (e.g., "Success", "Error")
    private String status;
    
    // The HTTP status code associated with the response (e.g., 200, 404)
    private Integer code;
    
}
