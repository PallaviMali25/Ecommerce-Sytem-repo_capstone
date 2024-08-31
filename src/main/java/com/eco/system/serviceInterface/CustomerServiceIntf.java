package com.eco.system.serviceInterface;

import java.util.List;

import com.eco.system.entity.Customer;
import com.eco.system.exception.CustomerNotFoundException;

public interface CustomerServiceIntf {

    List<Customer> getAllCustomers();

    Customer getCustomerById(Integer customerId) throws CustomerNotFoundException;

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Integer customerId, Customer customer) throws CustomerNotFoundException;

    void deleteCustomer(Integer customerId) throws CustomerNotFoundException;
}
