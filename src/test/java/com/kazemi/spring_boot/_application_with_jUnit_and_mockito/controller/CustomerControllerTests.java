package com.kazemi.spring_boot._application_with_jUnit_and_mockito.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kazemi.spring_boot._application_with_jUnit_and_mockito.model.Customer;
import com.kazemi.spring_boot._application_with_jUnit_and_mockito.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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

    @Test
    public void givenCustomerObject_whenCreateCustomer_thenReturnSavedCustomer() throws Exception{

        // given - precondition or setup
        given(customerService.saveCustomer(any(Customer.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName",
                        is(customer.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(customer.getLastName())))
                .andExpect(jsonPath("$.email",
                        is(customer.getEmail())));
    }

    // JUnit test for Get All customers REST API
    @Test
    public void givenListOfCustomers_whenGetAllCustomers_thenReturnCustomersList() throws Exception{
        // given - precondition or setup
        List<Customer> listOfCustomers = new ArrayList<>();
        listOfCustomers.add(Customer.builder().firstName("Fatemeh").lastName("Kazemi").email("fh.kazemi84@gmail.com").build());
        listOfCustomers.add(Customer.builder().firstName("Juli").lastName("Laundu").email("jlaundu@gmail.com").build());
        given(customerService.getAllCustomers()).willReturn(listOfCustomers);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/customers"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfCustomers.size())));
    }

    // positive scenario - valid customer id
    // JUnit test for GET customer by id REST API
    @Test
    public void givenCustomerId_whenGetCustomerById_thenReturnCustomerObject() throws Exception{
        // given - precondition or setup
        given(customerService.getCustomerById(customer.getId())).willReturn(Optional.of(customer));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/customers/{id}", customer.getId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(customer.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(customer.getLastName())))
                .andExpect(jsonPath("$.email", is(customer.getEmail())));
    }

    // negative scenario - valid customer id
    // JUnit test for GET customer by id REST API
    @Test
    public void givenInvalidCustomerId_whenGetCustomerById_thenReturnEmpty() throws Exception{
        // given - precondition or setup
        given(customerService.getCustomerById(customer.getId())).willReturn(Optional.empty());

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/customers/{id}", customer.getId()));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }
}
