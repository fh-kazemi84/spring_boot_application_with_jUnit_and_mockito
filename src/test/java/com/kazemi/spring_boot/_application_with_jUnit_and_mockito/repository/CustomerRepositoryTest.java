package com.kazemi.spring_boot._application_with_jUnit_and_mockito.repository;

import com.kazemi.spring_boot._application_with_jUnit_and_mockito.model.Customer;
import static org.assertj.core.api.Assertions.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * @author fh.kazemi
 **/

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    // JUnit test for save customer operation
    @DisplayName("JUnit test for save customer operation")
    @Test
    public void givenCustomerObject_whenSave_thenReturnSavedCustomer(){
        Customer customer= Customer.builder()
                .firstName("Fatemeh")
                .lastName("Kazemi")
                .email("fh.kazemi84@gmail.com")
                .build();
        Customer savedCustomer = customerRepository.save(customer);
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit test for get all customers operation")
    @Test
    public void givenCustomersList_whenFindAll_thenCustomersList(){
        Customer customer_1 = Customer.builder()
                .firstName("Fatemeh")
                .lastName("Kazemi")
                .email("fh.kazemi84@gmail.com")
                .build();

        Customer customer_2 = Customer.builder()
                .firstName("Juli")
                .lastName("Laundu")
                .email("jlaundu@gmail.com")
                .build();

        customerRepository.save(customer_1);
        customerRepository.save(customer_2);

        List<Customer> customerList = customerRepository.findAll();

        assertThat(customerList).isNotNull();
        assertThat(customerList.size()).isEqualTo(2);
    }

    // JUnit test for get customer by id operation
    @DisplayName("JUnit test for get customer by id operation")
    @Test
    public void givenCustomerObject_whenFindById_thenReturnCustomerObject(){
        Customer customer = Customer.builder()
                .firstName("Fatemeh")
                .lastName("Kazemi")
                .email("fh.kazemi84@gmail.com")
                .build();
        customerRepository.save(customer);

        Customer customerDB = customerRepository.findById(customer.getId()).get();

        assertThat(customerDB).isNotNull();
    }

    // JUnit test for get customer by email operation
    @DisplayName("JUnit test for get customer by email operation")
    @Test
    public void givenCustomerEmail_whenFindByEmail_thenReturnCustomerObject(){
        Customer customer = Customer.builder()
                .firstName("Fatemeh")
                .lastName("Kazemi")
                .email("fh.kazemi84@gmail.com")
                .build();
        customerRepository.save(customer);

        Customer customerDB = customerRepository.findByEmail(customer.getEmail()).get();

        assertThat(customerDB).isNotNull();
    }

    // JUnit test for update customer operation
    @DisplayName("JUnit test for update customer operation")
    @Test
    public void givenCustomerObject_whenUpdateCustomer_thenReturnUpdatedCustomer(){
        Customer customer = Customer.builder()
                .firstName("Fatemeh")
                .lastName("Kazemi")
                .email("fh.kazemi84@gmail.com")
                .build();
        customerRepository.save(customer);

        Customer savedCustomer = customerRepository.findById(customer.getId()).get();
        savedCustomer.setEmail("kazemi@gmail.com");
        savedCustomer.setFirstName("Fati");
        Customer updatedCustomer =  customerRepository.save(savedCustomer);

        assertThat(updatedCustomer.getEmail()).isEqualTo("kazemi@gmail.com");
        assertThat(updatedCustomer.getFirstName()).isEqualTo("Fati");
    }

    // JUnit test for delete customer operation
    @DisplayName("JUnit test for delete customer operation")
    @Test
    public void givenCustomerObject_whenDelete_thenRemoveCustomer(){
        Customer customer = Customer.builder()
                .firstName("Fatemeh")
                .lastName("Kazemi")
                .email("fh.kazemi84@gmail.com")
                .build();
        customerRepository.save(customer);

        customerRepository.deleteById(customer.getId());
        Optional<Customer> customerOptional = customerRepository.findById(customer.getId());

        assertThat(customerOptional).isEmpty();
    }

    // JUnit test for custom query using JPQL with index
    @DisplayName("JUnit test for custom query using JPQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnCustomerObject(){
        Customer customer = Customer.builder()
                .firstName("Fatemeh")
                .lastName("Kazemi")
                .email("fh.kazemi84@gmail.com")
                .build();
        customerRepository.save(customer);
        String firstName = "Fatemeh";
        String lastName = "Kazemi";

        Customer savedCustomer = customerRepository.findByJPQL(firstName, lastName);

        assertThat(savedCustomer).isNotNull();
    }
}
