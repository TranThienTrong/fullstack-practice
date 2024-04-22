package com.patecan.fullstackapp.repository;

import com.patecan.fullstackapp.AbstractTestContainerTest;
import com.patecan.fullstackapp.models.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class CustomerJPARepositoryImplTest {

    private CustomerJPARepositoryImpl customerJPARepositoryImpl;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerJPARepositoryImpl = new CustomerJPARepositoryImpl(customerRepository);
    }

    @AfterEach
    void tearDown(){}

    @Test
    void selectAllCustomers() {
        // Given

        // When
        customerJPARepositoryImpl.selectAllCustomers();

        // Then
        // __________ To know what function are called __________
        verify(customerRepository).findAll();

    }

    @Test
    void selectCustomerById() {
        // Given
        int id = 1;


        // When
        customerJPARepositoryImpl.selectCustomerById(id);

        // Then
        // __________ To know what function are called __________
        verify(customerRepository).findById(id);

    }

    @Test
    void insertCustomer() {
        // Given
        Customer customer = new Customer(
                "Tran Thien Trong",
                "trong@gmail.com",
                18
        );

        Customer customer2 = new Customer(
                "Tran Thien Trong 2",
                "trong2@gmail.com",
                18
        );

        // When
        customerJPARepositoryImpl.insertCustomer(customer);

        // Then
        // __________ To know what function are called __________
        verify(customerRepository).save(customer);
    }

    @Test
    void checkExistedEmail() {
    }
}