package com.kazemi.spring_boot._application_with_jUnit_and_mockito.repository;


import com.kazemi.spring_boot._application_with_jUnit_and_mockito.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author fh.kazemi
 **/

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    // define custom query using JPQL with index params
    @Query("select c from Customer c where c.firstName = ?1 and c.lastName = ?2")
    Customer findByJPQL(String firstName, String lastName);

    // define custom query using JPQL with named params
    @Query("select c from Customer c where c.firstName =:firstName and c.lastName =:lastName")
    Customer findByJPQLNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);

    // define custom query using Native SQL with index params
    @Query(value = "select * from customers c where c.first_name =?1 and c.last_name =?2",
            nativeQuery = true)
    Customer findByNativeSQL(String firstName, String lastName);

    // define custom query using Native SQL with named params
    @Query(value = "select * from customers c where c.first_name =:firstName and c.last_name =:lastName",
            nativeQuery = true)
    Customer findByNativeSQLNamed(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
