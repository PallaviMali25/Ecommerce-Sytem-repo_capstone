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

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = EcommerceSystemMain.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void testGetAllCustomers() {
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        List<Customer> customers = customerService.getAllCustomers();

        assertEquals(2, customers.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testGetCustomerById_Success() throws CustomerNotFoundException {
        Customer customer = new Customer();
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        Customer foundCustomer = customerService.getCustomerById(1);

        assertNotNull(foundCustomer);
        verify(customerRepository, times(1)).findById(1);
    }

    @Test
    void testGetCustomerById_NotFound() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(1));
    }

    @Test
    void testCreateCustomer() {
        Customer customer = new Customer();
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer createdCustomer = customerService.createCustomer(customer);

        assertNotNull(createdCustomer);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testUpdateCustomer_Success() throws CustomerNotFoundException {
        Customer existingCustomer = new Customer();
        existingCustomer.setCustomerId(1);
        when(customerRepository.findById(1)).thenReturn(Optional.of(existingCustomer));

        Customer updatedCustomer = new Customer();
        updatedCustomer.setName("Updated Name");
        updatedCustomer.setEmail("updated@example.com");
        updatedCustomer.setPassword("newpassword");
        updatedCustomer.setAddress("New Address");

        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        Customer result = customerService.updateCustomer(1, updatedCustomer);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        assertEquals("updated@example.com", result.getEmail());
        assertEquals("newpassword", result.getPassword());
        assertEquals("New Address", result.getAddress());
        verify(customerRepository, times(1)).findById(1);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testUpdateCustomer_NotFound() {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setName("Updated Name");

        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(1, updatedCustomer));
    }

    @Test
    void testDeleteCustomer_Success() throws CustomerNotFoundException {
        Customer customer = new Customer();
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        customerService.deleteCustomer(1);

        verify(cartItemRepository, times(1)).deleteByCustomerCustomerId(1);
        verify(customerRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteCustomer_NotFound() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(1));
    }
}
