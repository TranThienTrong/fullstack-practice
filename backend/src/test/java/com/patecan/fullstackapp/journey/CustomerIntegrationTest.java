package com.patecan.fullstackapp.journey;

import com.github.javafaker.Faker;
import com.patecan.fullstackapp.models.Customer;
import com.patecan.fullstackapp.models.CustomerRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    void canCreateCustomer() {
        // Create Payload
        Faker faker = new Faker();
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        int age =  18;

        CustomerRequest customer = new CustomerRequest(name, email, age);

        // Send POST Request
        webClient.post()
                .uri("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(customer), CustomerRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // Verify
        List<Customer> customerList  = webClient.get()
                .uri("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {})
                .returnResult()
                .getResponseBody();

        Customer expected = new Customer(name, email, age);

        assertThat(customerList)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expected);


    }

    @Test
    void canFindCustomer() {
        // Create Payload
        Faker faker = new Faker();
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        int age =  18;

        CustomerRequest customer = new CustomerRequest(name, email, age);

        // Send POST Request
        webClient.post()
                .uri("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(customer), CustomerRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // Verify
        List<Customer> customerList  = webClient.get()
                .uri("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {})
                .returnResult()
                .getResponseBody();


        if (customerList != null) {
            Customer foundCustomer = customerList.stream().filter(c -> c.getEmail().equals(email)).findFirst().get();

            webClient.get()
                    .uri("/api/v1/customers"+"/{id}", foundCustomer.getId())
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody(new ParameterizedTypeReference<Customer>() {})
                    .isEqualTo(foundCustomer);
        }
    }
}
