package com.kazemi.spring_boot._application_with_jUnit_and_mockito.controller;

import com.kazemi.spring_boot._application_with_jUnit_and_mockito.service.CustomerService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
