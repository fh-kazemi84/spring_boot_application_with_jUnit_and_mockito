package com.kazemi.spring_boot._application_with_jUnit_and_mockito.service.impl;

import com.kazemi.spring_boot._application_with_jUnit_and_mockito.exception.ResourceNotFoundException;
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
        Optional<Customer> savedCustomer = customerRepository.findByEmail(customer.getEmail());
        if(savedCustomer.isPresent()){
            throw new ResourceNotFoundException("Customer already exist with given email:" + customer.getEmail());
        }
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer updateCustomer(Customer updatedCustomer) {
        return customerRepository.save(updatedCustomer);
    }

    @Override
    public void deleteCustomer(long id) {

    }
}
