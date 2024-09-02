package com.demo.eurekaClient1.serviceTest;

import com.eco.system.EcommerceSystemMain;
import com.eco.system.entity.Customer;
import com.eco.system.exception.CustomerNotFoundException;
import com.eco.system.repository.CartItemRepository;
import com.eco.system.repository.CustomerRepository;
import com.eco.system.serviceImplementation.CustomerService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test class for the CustomerService.
 * This class tests the functionality of the CustomerService class
 * using mocked dependencies.
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = EcommerceSystemMain.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository; // Mocked CustomerRepository dependency

    @Mock
    private CartItemRepository cartItemRepository; // Mocked CartItemRepository dependency

    @InjectMocks
    private CustomerService customerService; // Injected instance of CustomerService with mocks

    /**
     * Test case for retrieving all customers.
     * It verifies that the service retrieves the correct list of customers.
     */
    @Test
    void testGetAllCustomers() {
        // Given: Mock two customer entities
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        
        // When: Mock the findAll method to return the list of customers
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        // When: Call the getAllCustomers method
        List<Customer> customers = customerService.getAllCustomers();

        // Then: Verify the results
        assertEquals(2, customers.size()); // Ensure the size of the list is 2
        verify(customerRepository, times(1)).findAll(); // Ensure findAll was called exactly once
    }

    /**
     * Test case for retrieving a customer by its ID when the customer exists.
     * It verifies that the service retrieves the correct customer.
     */
    @Test
    void testGetCustomerById_Success() throws CustomerNotFoundException {
        // Given: Mock the findById method to return a customer
        Customer customer = new Customer();
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        // When: Call the getCustomerById method
        Customer foundCustomer = customerService.getCustomerById(1);

        // Then: Verify the results
        assertNotNull(foundCustomer); // Ensure the customer is not null
        verify(customerRepository, times(1)).findById(1); // Ensure findById was called exactly once
    }

    /**
     * Test case for retrieving a customer by its ID when the customer is not found.
     * It verifies that the service throws a CustomerNotFoundException.
     */
    @Test
    void testGetCustomerById_NotFound() {
        // Given: Mock the findById method to return an empty result
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        // Then: Verify that the service throws the appropriate exception
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(1));
    }

    /**
     * Test case for creating a new customer.
     * It verifies that the service correctly saves the customer.
     */
    @Test
    void testCreateCustomer() {
        // Given: Create a new customer entity
        Customer customer = new Customer();
        
        // When: Mock the save method to return the same customer
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // When: Call the createCustomer method
        Customer createdCustomer = customerService.createCustomer(customer);

        // Then: Verify the customer has been saved correctly
        assertNotNull(createdCustomer); // Ensure the created customer is not null
        verify(customerRepository, times(1)).save(customer); // Ensure save was called exactly once
    }

    /**
     * Test case for updating an existing customer when the customer is found.
     * It verifies that the service updates the customer with the correct details.
     */
    @Test
    void testUpdateCustomer_Success() throws CustomerNotFoundException {
        // Given: Mock the existing customer entity
        Customer existingCustomer = new Customer();
        existingCustomer.setCustomerId(1);
        when(customerRepository.findById(1)).thenReturn(Optional.of(existingCustomer));

        // Given: Define a new customer with updated values
        Customer updatedCustomer = new Customer();
        updatedCustomer.setName("Updated Name");
        updatedCustomer.setEmail("updated@example.com");
        updatedCustomer.setPassword("newpassword");
        updatedCustomer.setAddress("New Address");

        // When: Mock the save method to return the updated customer
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        // When: Call the updateCustomer method
        Customer result = customerService.updateCustomer(1, updatedCustomer);

        // Then: Verify the customer has been updated correctly
        assertNotNull(result); // Ensure the result is not null
        assertEquals("Updated Name", result.getName()); // Verify the name
        assertEquals("updated@example.com", result.getEmail()); // Verify the email
        assertEquals("newpassword", result.getPassword()); // Verify the password
        assertEquals("New Address", result.getAddress()); // Verify the address

        // Verify interactions
        verify(customerRepository, times(1)).findById(1); // Ensure findById was called exactly once
        verify(customerRepository, times(1)).save(any(Customer.class)); // Ensure save was called exactly once
    }

    /**
     * Test case for updating a customer when the customer is not found.
     * It verifies that the service throws a CustomerNotFoundException.
     */
    @Test
    void testUpdateCustomer_NotFound() {
        // Given: Define a new customer with updated values
        Customer updatedCustomer = new Customer();

        // Given: Mock the findById method to return an empty result
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        // Then: Verify that the service throws the appropriate exception
        assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(1, updatedCustomer));
    }

    /**
     * Test case for deleting a customer when the customer is found.
     * It verifies that the service deletes the customer and associated cart items.
     */
    @Test
    void testDeleteCustomer_Success() throws CustomerNotFoundException {
        // Given: Mock the existing customer entity
        Customer customer = new Customer();
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        // When: Call the deleteCustomer method
        customerService.deleteCustomer(1);

        // Then: Verify the customer and associated cart items are deleted
        verify(cartItemRepository, times(1)).deleteByCustomerCustomerId(1); // Ensure cart items are deleted
        verify(customerRepository, times(1)).deleteById(1); // Ensure the customer is deleted
    }

    /**
     * Test case for deleting a customer when the customer is not found.
     * It verifies that the service throws a CustomerNotFoundException.
     */
    @Test
    void testDeleteCustomer_NotFound() {
        // Given: Mock the findById method to return an empty result
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        // Then: Verify that the service throws the appropriate exception
        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(1));
    }
}
