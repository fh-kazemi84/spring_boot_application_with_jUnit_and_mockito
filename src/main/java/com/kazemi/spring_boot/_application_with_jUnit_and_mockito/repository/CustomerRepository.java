package com.kazemi.spring_boot._application_with_jUnit_and_mockito.repository;

import com.kazemi.spring_boot._application_with_jUnit_and_mockito.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fh.kazemi
 **/

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
