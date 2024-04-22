package com.patecan.fullstackapp;

import com.patecan.fullstackapp.beans.CustomerRowMapper;
import com.patecan.fullstackapp.models.Customer;
import com.patecan.fullstackapp.repository.CustomerJDBCRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import java.util.List;
import java.util.Optional;

import static com.patecan.fullstackapp.AbstractTestContainerTest.getDataSource;
import static org.assertj.core.api.Assertions.assertThat;

class CustomerJDBCRepositoryImplTest extends AbstractTestContainerTest {
    private CustomerJDBCRepositoryImpl repositoryImpl;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();



    @BeforeEach
    void setUp() {
        repositoryImpl = new CustomerJDBCRepositoryImpl(
            new JdbcTemplate(getDataSource()),
                customerRowMapper
        );
    }

    @Test
    void testSelectAllCustomers(){
        // Given
        Customer customer = new Customer(
                "Tran Thien Trong",
                "trong@gmail.com",
                18
        );
        repositoryImpl.insertCustomer(customer);

        // When
        List<Customer> customersList = repositoryImpl.selectAllCustomers();

        // Then
        assertThat(customersList).isNotEmpty();
    }

    @Test
    void testSelectCustomerById(){
        // Given
        Customer customer = new Customer(
                "Tran Thien Trong",
                "trong@gmail.com",
                18
        );
        repositoryImpl.insertCustomer(customer);

        // When
        int foundId = repositoryImpl.selectAllCustomers()
                .stream()
                .filter(custm -> custm.getEmail().equals("trong@gmail.com"))
                        .map(Customer::getId)
                                .findFirst()
                                        .orElseThrow();

        Optional<Customer> foundCustomer = repositoryImpl.selectCustomerById(foundId);

        // Then
        assertThat(foundCustomer).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(foundId);
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
        });
    }



}
