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

@Service
public class CustomerService implements CustomerServiceIntf{

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    
    @Override
    public Customer getCustomerById(Integer customerId) throws CustomerNotFoundException {
        return customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));
    }

//        for (Order r : customer.getOrders()) {
//            r.setCustomer(null);
//        }
     
    
    


    public Customer createCustomer(Customer customer) {
         customer = customerRepository.save(customer);
        
//        for (Order r : customer.getOrders()) {
//            r.setCustomer(null);
//        }
        return customer;
    }

    public Customer updateCustomer(Integer customerId, Customer customer) throws CustomerNotFoundException {
       // Customer customer = getCustomerById(customerId);
    	Customer cust = customerRepository.findById(customerId)
        .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));
    	
    	cust.setName(customer.getName());
    	cust.setEmail(customer.getEmail());
    	cust.setPassword(customer.getPassword());
    	cust.setAddress(customer.getAddress());
    	cust=customerRepository.save(cust);
//        
//        for (Order r : customer.getOrders()) {
//            r.setCustomer(null);
//        }
        return cust;
    }

    public void deleteCustomer(Integer customerId) throws CustomerNotFoundException {
      
    	Customer customer = customerRepository.findById(customerId)
    	 
    			.orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));
    	cartItemRepository.deleteByCustomerCustomerId(customerId);
        customerRepository.deleteById(customerId);
    }
}