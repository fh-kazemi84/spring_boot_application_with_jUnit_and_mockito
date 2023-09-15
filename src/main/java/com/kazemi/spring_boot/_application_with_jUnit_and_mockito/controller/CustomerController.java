package com.kazemi.spring_boot._application_with_jUnit_and_mockito.controller;

import com.kazemi.spring_boot._application_with_jUnit_and_mockito.model.Customer;
import com.kazemi.spring_boot._application_with_jUnit_and_mockito.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author fh.kazemi
 **/

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody Customer customer){
        return customerService.saveCustomer(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") long customerId){
        return customerService.getCustomerById(customerId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long customerId,
                                                   @RequestBody Customer customer){
        return customerService.getCustomerById(customerId)
                .map(savedCustomer-> {

                    savedCustomer.setFirstName(customer.getFirstName());
                    savedCustomer.setLastName(customer.getLastName());
                    savedCustomer.setEmail(customer.getEmail());

                    Customer updatedCustomer = customerService.updateCustomer(savedCustomer);
                    return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);

                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
