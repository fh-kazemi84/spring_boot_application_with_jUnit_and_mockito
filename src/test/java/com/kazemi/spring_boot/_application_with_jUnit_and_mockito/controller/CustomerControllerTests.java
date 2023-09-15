package com.kazemi.spring_boot._application_with_jUnit_and_mockito.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kazemi.spring_boot._application_with_jUnit_and_mockito.model.Customer;
import com.kazemi.spring_boot._application_with_jUnit_and_mockito.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


/**
 * @author fh.kazemi
 **/

@WebMvcTest
public class CustomerControllerTests {

    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;


    private ObjectMapper objectMapper;

    private Customer customer;

    @BeforeEach
    public void setup(){
        customer = Customer.builder()
                .id(1L)
                .firstName("Fatemeh")
                .lastName("Kazemi")
                .email("fh.kazemi84@gmail.com")
                .build();
    }
}
