package com.kazemi.spring_boot._application_with_jUnit_and_mockito.Integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kazemi.spring_boot._application_with_jUnit_and_mockito.model.Customer;
import com.kazemi.spring_boot._application_with_jUnit_and_mockito.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author fh.kazemi
 **/

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CustomerControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;
    private Customer customer;

    @BeforeEach
    void setup(){
        customerRepository.deleteAll();
        customer = Customer.builder()
                .id(1L)
                .firstName("Fatemeh")
                .lastName("Kazemi")
                .email("fh.kazemi84@gmail.com")
                .build();
    }

    @Test
    public void givenCustomerObject_whenCreateCustomer_thenReturnSavedCustomer() throws Exception{

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
        customerRepository.saveAll(listOfCustomers);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/customers"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfCustomers.size())));
    }

}
