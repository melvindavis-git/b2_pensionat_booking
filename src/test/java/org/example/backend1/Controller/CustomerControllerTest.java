package org.example.backend1.Controller;


import jakarta.transaction.Transactional;
import org.example.backend1.BaseTest;
import org.example.backend1.DTO.CustomerDTO;
import org.example.backend1.Model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerTest extends BaseTest {


    @Test
    void getAllCustomersTest() {
        restTestClient.get()
                .uri("http://localhost:8080/api/customers")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].name").isEqualTo("Mikael")
                .jsonPath("$[1].name").isEqualTo("Tim")
                .jsonPath("$[2].name").isEqualTo("Melvin");

    }

    @Test
    void registerCustomersTest() {
        restTestClient.post()
                .uri("http://localhost:8080/api/customers/register")
                .body(new CustomerDTO(null, "Testsson", "testsson@test.se", "0709112233"))
                .exchange()
                .expectStatus().isOk();
        List<Customer> all = customerRepository.findAll();

        long count = all.stream().
                filter(customer -> customer.getName().equals("Testsson"))
                .count();

        assertEquals(1, count);
        assertEquals("Testsson", all.getLast().getName());
    }

    @Test
    @Transactional
    void deleteByIdTest() {
        List<Customer> customers = customerRepository.findAll();
        Customer customer = customers.getFirst();

        restTestClient.delete()
                .uri("http://localhost:8080/api/customers/delete/" + customer.getId())
                .exchange()
                .expectStatus().isEqualTo(500);
        System.out.println(customer.getId());

        customers = customerRepository.findAll();

        assertTrue(customers.contains(customer));

        bookingRepository.deleteBookingByCustomer(customer);

        restTestClient.delete()
                .uri("http://localhost:8080/api/customers/delete/" + customer.getId())
                .exchange()
                .expectStatus().isOk();

        customers = customerRepository.findAll();
        assertFalse(customers.contains(customer));
    }
}