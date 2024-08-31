package com.eco.system.exception;

/**
 * Exception thrown when a customer is not found in the system.
 * Extends Exception to provide checked exception handling.
 */
public class CustomerNotFoundException extends Exception {

    /**
     * Constructs a new CustomerNotFoundException with the specified detail message.
     * 
     * @param msg the detail message
     */
    public CustomerNotFoundException(String msg) {
        super(msg);
    }
}
