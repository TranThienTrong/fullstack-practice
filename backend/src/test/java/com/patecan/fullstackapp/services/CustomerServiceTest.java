package com.patecan.fullstackapp.services;

import com.patecan.fullstackapp.dao.CustomerDao;
import com.patecan.fullstackapp.exception.DuplicateExceptionResponse;
import com.patecan.fullstackapp.exception.NotFoundResponse;
import com.patecan.fullstackapp.models.Customer;
import com.patecan.fullstackapp.models.CustomerRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDao customerDao;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService(customerDao);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllCustomers() {
        // When
        customerService.getAllCustomers();

        // Then
        verify(customerDao).selectAllCustomers();
    }

    @Test
    void testGetCustomer() {
        // Given
        int id = 10;
        Customer customer = new Customer(
                10,
                "Tran Thien Trong",
                "trong@gmail.com",
                18
        );

        // When
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        // Then
        Customer result = customerService.getCustomer(id);
        assertEquals(customer, result);
    }

    @Test
    void testThrowExceptionWhenNotFoundCustomer() {
        // Given
        int id = 10;
        Customer customer = new Customer(
                10,
                "Tran Thien Trong",
                "trong@gmail.com",
                18
        );

        // When
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(()-> customerService.getCustomer(id))
                .isInstanceOf(NotFoundResponse.class)
                .hasMessage("Customer with ID [%s] not found".formatted(id));
    }

    @Test
    void addCustomer() {
        // Given
        String email = "trong@gmail.com";
        CustomerRequest customerRequest = new CustomerRequest("trong", email, 18);

        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);

        // When
        when(customerDao.checkExistedEmail(email)).thenReturn(false);

        // Then
        customerService.addCustomer(customerRequest);

        verify(customerDao).insertCustomer(argumentCaptor.capture());

        Customer copiedCustomer = argumentCaptor.getValue();

        assertThat(copiedCustomer.getName()).isEqualTo(customerRequest.name());
        assertThat(copiedCustomer.getEmail()).isEqualTo(customerRequest.email());
        assertThat(copiedCustomer.getAge()).isEqualTo(customerRequest.age());

    }

    @Test
    void throwDuplicateExceptionWhenAddCustomer() {
        // Given
        String email = "trong@gmail.com";
        CustomerRequest customerRequest = new CustomerRequest("trong", email, 18);

        // When
        when(customerDao.checkExistedEmail(email)).thenReturn(true);

        // Then
        assertThatThrownBy(()-> customerService.addCustomer(customerRequest)).isInstanceOf(DuplicateExceptionResponse.class);

        verify(customerDao, never()).insertCustomer(any());
    }
}