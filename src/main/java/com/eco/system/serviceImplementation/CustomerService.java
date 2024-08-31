package com.eco.system.serviceImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eco.system.beans.CustomerDTO;
import com.eco.system.entity.Customer;
import com.eco.system.entity.Order;
import com.eco.system.exception.CustomerNotFoundException;
import com.eco.system.repository.CartItemRepository;
import com.eco.system.repository.CustomerRepository;
import com.eco.system.serviceInterface.CustomerServiceIntf;

@Service // Indicates that this class is a Spring service component
public class CustomerService implements CustomerServiceIntf {

    @Autowired
    private CustomerRepository customerRepository; // Injects the CustomerRepository for database operations
    
    @Autowired
    private CartItemRepository cartItemRepository; // Injects the CartItemRepository for handling cart items related to customers

    /**
     * Retrieves all customers from the database.
     * 
     * @return List of all customers
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll(); // Finds and returns all customers
    }

    /**
     * Retrieves a customer by their ID.
     * 
     * @param customerId the ID of the customer to retrieve
     * @return the customer with the specified ID
     * @throws CustomerNotFoundException if no customer is found with the given ID
     */
    @Override
    public Customer getCustomerById(Integer customerId) throws CustomerNotFoundException {
        return customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));
    }

    /**
     * Creates a new customer in the database.
     * 
     * @param customer the customer entity to create
     * @return the created customer entity
     */
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer); // Saves and returns the created customer
    }

    /**
     * Updates an existing customer in the database.
     * 
     * @param customerId the ID of the customer to update
     * @param customer the customer entity with updated information
     * @return the updated customer entity
     * @throws CustomerNotFoundException if no customer is found with the given ID
     */
    public Customer updateCustomer(Integer customerId, Customer customer) throws CustomerNotFoundException {
        Customer cust = customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));
        
        // Updates customer details
        cust.setName(customer.getName());
        cust.setEmail(customer.getEmail());
        cust.setPassword(customer.getPassword());
        cust.setAddress(customer.getAddress());

        return customerRepository.save(cust); // Saves and returns the updated customer
    }

    /**
     * Deletes a customer from the database.
     * 
     * @param customerId the ID of the customer to delete
     * @throws CustomerNotFoundException if no customer is found with the given ID
     */
    public void deleteCustomer(Integer customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));
        
        cartItemRepository.deleteByCustomerCustomerId(customerId); // Deletes all cart items associated with the customer
        customerRepository.deleteById(customerId); // Deletes the customer by ID
    }
}
