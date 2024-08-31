package com.eco.system.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.eco.system.beans.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions when a customer is not found.
     * 
     * @param ex the CustomerNotFoundException
     * @return a ResponseEntity with a custom ApiResponse and NOT_FOUND status
     */
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiResponse> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        ApiResponse res = new ApiResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }
    
    /**
     * Handles exceptions when a cart item is not found.
     * 
     * @param ex the CartItemNotFoundException
     * @return a ResponseEntity with a custom ApiResponse and NOT_FOUND status
     */
    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<ApiResponse> handleCartItemNotFoundException(CartItemNotFoundException ex) {
        ApiResponse res = new ApiResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }
    
    /**
     * Handles exceptions when a product is not found.
     * 
     * @param ex the ProductNotFoundException
     * @return a ResponseEntity with a custom ApiResponse and NOT_FOUND status
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse> handleProductNotFoundException(ProductNotFoundException ex) {
        ApiResponse res = new ApiResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles exceptions when an order is not found.
     * 
     * @param ex the OrderNotFoundException
     * @return a ResponseEntity with a custom ApiResponse and NOT_FOUND status
     */
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiResponse> handleOrderNotFoundException(OrderNotFoundException ex) {
        ApiResponse res = new ApiResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles validation exceptions for invalid method arguments.
     * 
     * @param ex the MethodArgumentNotValidException
     * @return a ResponseEntity with a map of field errors and BAD_REQUEST status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles any other exceptions that are not specifically handled.
     * 
     * @param ex the Exception
     * @return a ResponseEntity with a generic error message and INTERNAL_SERVER_ERROR status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGlobalException(Exception ex) {
        ApiResponse res = new ApiResponse("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
