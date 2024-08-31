package com.eco.system.controller;

// Import necessary classes and libraries
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eco.system.entity.Customer;
import com.eco.system.exception.CustomerNotFoundException;
import com.eco.system.serviceImplementation.CustomerService;

import jakarta.validation.Valid;

@RestController // Indicates that this class is a REST controller
@RequestMapping("/customers") // Base URL for all endpoints related to customers
@CrossOrigin(origins = "http://localhost:8081") // Enables CORS for the specified origin
@Validated // Enables validation for request data in the controller
public class CustomerController {

    @Autowired // Injects the CustomerService bean for dependency injection
    private CustomerService customerService;

    /**
     * GET endpoint to retrieve all customers.
     * 
     * @return List of all customers.
     */
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    /**
     * GET endpoint to retrieve a customer by its ID.
     * 
     * @param customerId ID of the customer to retrieve.
     * @return ResponseEntity containing the customer and HTTP status.
     * @throws CustomerNotFoundException if the customer is not found.
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer customerId) throws CustomerNotFoundException {
        Customer customer = customerService.getCustomerById(customerId);
        return new ResponseEntity<>(customer, HttpStatus.OK); // Returns the customer with a 200 OK status
    }

    /**
     * POST endpoint to create a new customer.
     * 
     * @param customer Customer object to be created.
     * @return ResponseEntity containing the created customer and HTTP status.
     */
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED); // Returns 201 Created status
    }

    /**
     * PUT endpoint to update an existing customer.
     * 
     * @param customerId ID of the customer to update.
     * @param customer Customer object containing updated data.
     * @return ResponseEntity containing the updated customer and HTTP status.
     * @throws CustomerNotFoundException if the customer is not found.
     */
    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer customerId, @Valid @RequestBody Customer customer) throws CustomerNotFoundException {
        Customer updatedCustomer = customerService.updateCustomer(customerId, customer);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK); // Returns 200 OK status
    }

    /**
     * DELETE endpoint to delete a customer by its ID.
     * 
     * @param customerId ID of the customer to delete.
     * @return ResponseEntity with HTTP status indicating the result.
     * @throws CustomerNotFoundException if the customer is not found.
     */
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer customerId) throws CustomerNotFoundException {
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Returns 204 No Content status indicating successful deletion
    }
}
