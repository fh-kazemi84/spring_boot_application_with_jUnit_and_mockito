package com.kazemi.spring_boot._application_with_jUnit_and_mockito.service.impl;

import com.kazemi.spring_boot._application_with_jUnit_and_mockito.model.Customer;
import com.kazemi.spring_boot._application_with_jUnit_and_mockito.repository.CustomerRepository;
import com.kazemi.spring_boot._application_with_jUnit_and_mockito.service.CustomerService;

import java.util.List;
import java.util.Optional;

/**
 * @author fh.kazemi
 **/

public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return null;
    }

    @Override
    public Optional<Customer> getCustomerById(long id) {
        return Optional.empty();
    }

    @Override
    public Customer updateCustomer(Customer updatedCustomer) {
        return null;
    }

    @Override
    public void deleteCustomer(long id) {

    }
}
