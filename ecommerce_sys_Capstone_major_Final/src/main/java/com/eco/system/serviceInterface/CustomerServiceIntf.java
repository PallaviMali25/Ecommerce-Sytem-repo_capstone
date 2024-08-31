package com.eco.system.serviceInterface;

import java.util.List;

import com.eco.system.entity.Customer;
import com.eco.system.exception.CustomerNotFoundException;

public interface CustomerServiceIntf {

    /**
     * Retrieves all customers.
     * 
     * @return List of all Customer entities.
     */
    List<Customer> getAllCustomers();

    /**
     * Retrieves a customer by its ID.
     * 
     * @param customerId The ID of the customer.
     * @return The Customer entity with the specified ID.
     * @throws CustomerNotFoundException if no customer is found with the specified ID.
     */
    Customer getCustomerById(Integer customerId) throws CustomerNotFoundException;

    /**
     * Creates a new customer.
     * 
     * @param customer The Customer entity to be created.
     * @return The newly created Customer entity.
     */
    Customer createCustomer(Customer customer);

    /**
     * Updates an existing customer identified by its ID with the provided customer details.
     * 
     * @param customerId The ID of the customer to be updated.
     * @param customer The Customer entity containing the updated details.
     * @return The updated Customer entity.
     * @throws CustomerNotFoundException if no customer is found with the specified ID.
     */
    Customer updateCustomer(Integer customerId, Customer customer) throws CustomerNotFoundException;

    /**
     * Deletes a customer by its ID.
     * 
     * @param customerId The ID of the customer to be deleted.
     * @throws CustomerNotFoundException if no customer is found with the specified ID.
     */
    void deleteCustomer(Integer customerId) throws CustomerNotFoundException;
}
