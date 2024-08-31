package com.eco.system.exception;

/**
 * Exception thrown when a product is not found in the system.
 * Extends Exception to provide checked exception handling.
 */
public class ProductNotFoundException extends Exception {

    /**
     * Constructs a new ProductNotFoundException with the specified detail message.
     * 
     * @param msg the detail message
     */
    public ProductNotFoundException(String msg) {
        super(msg);
    }
}
