package com.kazemi.spring_boot._application_with_jUnit_and_mockito.service;

import com.kazemi.spring_boot._application_with_jUnit_and_mockito.exception.ResourceNotFoundException;
import com.kazemi.spring_boot._application_with_jUnit_and_mockito.model.Customer;
import com.kazemi.spring_boot._application_with_jUnit_and_mockito.repository.CustomerRepository;
import com.kazemi.spring_boot._application_with_jUnit_and_mockito.service.impl.CustomerServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author fh.kazemi
 **/

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTests {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

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

    // JUnit test for saveCustomer method
    @DisplayName("JUnit test for saveCustomer method")
    @Test
    public void givenCustomerObject_whenSaveCustomer_thenReturnCustomerObject(){
        // given
        given(customerRepository.findByEmail(customer.getEmail()))
                .willReturn(Optional.empty());

        given(customerRepository.save(customer)).willReturn(customer);

        System.out.println(customerRepository);
        System.out.println(customerService);

        // when
        Customer savedCustomer = customerService.saveCustomer(customer);

        System.out.println(savedCustomer);

        // then
        assertThat(savedCustomer).isNotNull();
    }

    // JUnit test for saveCustomer method
    @DisplayName("JUnit test for saveCustomer method which throws exception")
    @Test
    public void givenExistingEmail_whenSaveCustomer_thenThrowsException(){
        // given
        given(customerRepository.findByEmail(customer.getEmail()))
                .willReturn(Optional.of(customer));

        System.out.println(customerRepository);
        System.out.println(customerService);

        // when
        assertThrows(ResourceNotFoundException.class, () -> {
            customerService.saveCustomer(customer);
        });

        //then
        verify(customerRepository, never()).save(any(Customer.class));
    }

    // JUnit test for getAllCustomers method
    @DisplayName("JUnit test for getAllCustomers method")
    @Test
    public void givenCustomersList_whenGetAllCustomers_thenReturnCustomersList(){
        //given
        Customer customer_1 = Customer.builder()
                .id(2L)
                .firstName("Juli")
                .lastName("Laundu")
                .email("jlaundu@gmail.com")
                .build();

        given(customerRepository.findAll()).willReturn(List.of(customer,customer_1));

        //when
        List<Customer> customerList = customerService.getAllCustomers();

        //then
        assertThat(customerList).isNotNull();
        assertThat(customerList.size()).isEqualTo(2);
    }

    // JUnit test for getAllCustomers method
    @DisplayName("JUnit test for getAllCustomers method (negative scenario)")
    @Test
    public void givenEmptyCustomersList_whenGetAllCustomers_thenReturnEmptyCustomersList(){

        //given
        given(customerRepository.findAll()).willReturn(Collections.emptyList());

        //when
        List<Customer> customerList = customerService.getAllCustomers();

        //then
        assertThat(customerList).isEmpty();
        assertThat(customerList.size()).isEqualTo(0);
    }

    // JUnit test for getCustomerById method
    @DisplayName("JUnit test for getCustomerById method")
    @Test
    public void givenCustomerId_whenGetCustomerById_thenReturnCustomerObject(){
        //given
        given(customerRepository.findById(1L)).willReturn(Optional.of(customer));

        //when
        Customer savedCustomer = customerService.getCustomerById(customer.getId()).get();

        //then
        assertThat(savedCustomer).isNotNull();
    }

    // JUnit test for updateCustomer method
    @DisplayName("JUnit test for updateCustomer method")
    @Test
    public void givenCustomerObject_whenUpdateCustomer_thenReturnUpdatedCustomer(){
        //given
        given(customerRepository.save(customer)).willReturn(customer);
        customer.setEmail("kazemi@gmail.com");
        customer.setFirstName("Fati");

        //when
        Customer updatedCustomer = customerService.updateCustomer(customer);

        //then
        assertThat(updatedCustomer.getEmail()).isEqualTo("kazemi@gmail.com");
        assertThat(updatedCustomer.getFirstName()).isEqualTo("Fati");
    }

    // JUnit test for deleteCustomer method
    @DisplayName("JUnit test for deleteCustomer method")
    @Test
    public void givenCustomerId_whenDeleteCustomer_thenNothing(){
        //given
        long customerId = 1L;

        willDoNothing().given(customerRepository).deleteById(customerId);

        //when
        customerService.deleteCustomer(customerId);

        //then
        verify(customerRepository, times(1)).deleteById(customerId);
    }
}
