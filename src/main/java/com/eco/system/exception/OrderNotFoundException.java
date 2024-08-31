package com.eco.system.exception;

/**
 * Exception thrown when an order is not found in the system.
 * Extends RuntimeException to provide unchecked exception handling.
 */
public class OrderNotFoundException extends RuntimeException {

    /**
     * Constructs a new OrderNotFoundException with the specified detail message.
     * 
     * @param message the detail message
     */
    public OrderNotFoundException(String message) {
        super(message);
    }
}
