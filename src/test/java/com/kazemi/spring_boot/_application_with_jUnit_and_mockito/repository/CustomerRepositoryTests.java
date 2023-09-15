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
public class CustomerRepositoryTests {

    @Autowired
    private CustomerRepository customerRepository;

    // JUnit test for save customer operation
    @DisplayName("JUnit test for save customer operation")
    @Test
    public void givenCustomerObject_whenSave_thenReturnSavedCustomer(){

        //given - precondition or setup
        Customer customer= Customer.builder()
                .firstName("Fatemeh")
                .lastName("Kazemi")
                .email("fh.kazemi84@gmail.com")
                .build();

        // when - action or the behaviour that we are going test
        Customer savedCustomer = customerRepository.save(customer);

        // then - verify the output
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit test for get all customers operation")
    @Test
    public void givenCustomersList_whenFindAll_thenCustomersList(){

        //given - precondition or setup
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

        // when -  action or the behaviour that we are going test
        List<Customer> customerList = customerRepository.findAll();

        // then - verify the output
        assertThat(customerList).isNotNull();
        assertThat(customerList.size()).isEqualTo(2);
    }

    // JUnit test for get customer by id operation
    @DisplayName("JUnit test for get customer by id operation")
    @Test
    public void givenCustomerObject_whenFindById_thenReturnCustomerObject(){

        // given - precondition or setup
        Customer customer = Customer.builder()
                .firstName("Fatemeh")
                .lastName("Kazemi")
                .email("fh.kazemi84@gmail.com")
                .build();
        customerRepository.save(customer);

        // when -  action or the behaviour that we are going test
        Customer customerDB = customerRepository.findById(customer.getId()).get();

        // then - verify the output
        assertThat(customerDB).isNotNull();
    }

    // JUnit test for get customer by email operation
    @DisplayName("JUnit test for get customer by email operation")
    @Test
    public void givenCustomerEmail_whenFindByEmail_thenReturnCustomerObject(){

        // given - precondition or setup
        Customer customer = Customer.builder()
                .firstName("Fatemeh")
                .lastName("Kazemi")
                .email("fh.kazemi84@gmail.com")
                .build();
        customerRepository.save(customer);

        // when -  action or the behaviour that we are going test
        Customer customerDB = customerRepository.findByEmail(customer.getEmail()).get();

        // then - verify the output
        assertThat(customerDB).isNotNull();
    }

    // JUnit test for update customer operation
    @DisplayName("JUnit test for update customer operation")
    @Test
    public void givenCustomerObject_whenUpdateCustomer_thenReturnUpdatedCustomer(){

        // given - precondition or setup
        Customer customer = Customer.builder()
                .firstName("Fatemeh")
                .lastName("Kazemi")
                .email("fh.kazemi84@gmail.com")
                .build();
        customerRepository.save(customer);

        // when -  action or the behaviour that we are going test
        Customer savedCustomer = customerRepository.findById(customer.getId()).get();
        savedCustomer.setEmail("kazemi@gmail.com");
        savedCustomer.setFirstName("Fati");
        Customer updatedCustomer =  customerRepository.save(savedCustomer);

        // then - verify the output
        assertThat(updatedCustomer.getEmail()).isEqualTo("kazemi@gmail.com");
        assertThat(updatedCustomer.getFirstName()).isEqualTo("Fati");
    }

    // JUnit test for delete customer operation
    @DisplayName("JUnit test for delete customer operation")
    @Test
    public void givenCustomerObject_whenDelete_thenRemoveCustomer(){

        // given - precondition or setup
        Customer customer = Customer.builder()
                .firstName("Fatemeh")
                .lastName("Kazemi")
                .email("fh.kazemi84@gmail.com")
                .build();
        customerRepository.save(customer);

        // when -  action or the behaviour that we are going test
        customerRepository.deleteById(customer.getId());
        Optional<Customer> customerOptional = customerRepository.findById(customer.getId());

        // then - verify the output
        assertThat(customerOptional).isEmpty();
    }

    // JUnit test for custom query using JPQL with index
    @DisplayName("JUnit test for custom query using JPQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnCustomerObject(){

        // given - precondition or setup
        Customer customer = Customer.builder()
                .firstName("Fatemeh")
                .lastName("Kazemi")
                .email("fh.kazemi84@gmail.com")
                .build();
        customerRepository.save(customer);
        String firstName = "Fatemeh";
        String lastName = "Kazemi";

        // when -  action or the behaviour that we are going test
        Customer savedCustomer = customerRepository.findByJPQL(firstName, lastName);

        // then - verify the output
        assertThat(savedCustomer).isNotNull();
    }

    // JUnit test for custom query using JPQL with Named params
    @DisplayName("JUnit test for custom query using JPQL with Named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnCustomerObject(){

        // given - precondition or setup
        Customer customer = Customer.builder()
                .firstName("Fatemeh")
                .lastName("Kazemi")
                .email("fh.kazemi84@gmail.com")
                .build();
        customerRepository.save(customer);

        // when -  action or the behaviour that we are going test
        Customer savedCustomer = customerRepository.findByJPQLNamedParams(customer.getFirstName(), customer.getLastName());

        // then - verify the output
        assertThat(savedCustomer).isNotNull();
    }

    // JUnit test for custom query using native SQL with index
    @DisplayName("JUnit test for custom query using native SQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnCustomerObject(){

        // given - precondition or setup
        Customer customer = Customer.builder()
                .firstName("Fatemeh")
                .lastName("Kazemi")
                .email("fh.kazemi84@gmail.com")
                .build();
        customerRepository.save(customer);

        // when -  action or the behaviour that we are going test
        Customer savedCustomer = customerRepository.findByNativeSQL(customer.getFirstName(), customer.getLastName());

        // then - verify the output
        assertThat(savedCustomer).isNotNull();
    }

    // JUnit test for custom query using native SQL with named params
    @DisplayName("JUnit test for custom query using native SQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnCustomerObject(){

        // given - precondition or setup
        Customer customer = Customer.builder()
                .firstName("Fatemeh")
                .lastName("Kazemi")
                .email("fh.kazemi84@gmail.com")
                .build();
        customerRepository.save(customer);

        // when -  action or the behaviour that we are going test
        Customer savedCustomer = customerRepository.findByNativeSQLNamed(customer.getFirstName(), customer.getLastName());

        // then - verify the output
        assertThat(savedCustomer).isNotNull();
    }
}
