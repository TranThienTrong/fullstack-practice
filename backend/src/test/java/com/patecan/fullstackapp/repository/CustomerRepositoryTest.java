package com.patecan.fullstackapp.repository;

import com.patecan.fullstackapp.AbstractTestContainerTest;
import com.patecan.fullstackapp.models.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestContainerTest {

    @Autowired
    private CustomerRepository customerRepository;



    @Test
    void selectAllCustomers() {
        // Given
        Customer customer = new Customer(
                "Tran Thien Trong",
                "trong@gmail.com",
                18
        );
        customerRepository.save(customer);

        // When
        List<Customer> customersList = customerRepository.findAll();

        // Then
        assertThat(customersList).isNotEmpty();
    }

    @Test
    void selectCustomerById() {
        // Given
        Customer customer = new Customer(
                "Tran Thien Trong",
                "trong@gmail.com",
                18
        );
        customerRepository.save(customer);
        // When
        int foundId = customerRepository.findAll()
                .stream()
                .filter(custm -> custm.getEmail().equals("trong@gmail.com"))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        Optional<Customer> foundCustomer = customerRepository.findById(foundId);

        // Then
        assertThat(foundCustomer).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(foundId);
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
        });
    }

    @Test
    void insertCustomer() {
    }

    @Test
    void checkExistedEmail() {
    }
}