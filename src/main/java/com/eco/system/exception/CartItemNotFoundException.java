package com.eco.system.exception;

/**
 * Exception thrown when a cart item is not found in the system.
 * Extends RuntimeException to provide unchecked exception handling.
 */
public class CartItemNotFoundException extends RuntimeException {

    /**
     * Constructs a new CartItemNotFoundException with the specified detail message.
     * 
     * @param message the detail message
     */
    public CartItemNotFoundException(String message) {
        super(message);
    }
}
