package com.kazemi.spring_boot._application_with_jUnit_and_mockito.service;

import com.kazemi.spring_boot._application_with_jUnit_and_mockito.model.Customer;

import java.util.List;
import java.util.Optional;

/**
 * @author fh.kazemi
 **/

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(long id);
    Customer updateCustomer(Customer updatedCustomer);
    void deleteCustomer(long id);
}
