package com.patecan.fullstackapp.repository;

import com.patecan.fullstackapp.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    // JPA auto recognize the method-name and imply the SQL Query underneath
    boolean existsCustomerByEmail(String email);
}
