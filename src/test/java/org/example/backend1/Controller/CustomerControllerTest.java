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
                .uri("http://localhost:8080/customers")
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
                .uri("http://localhost:8080/customers/register")
                .body(new CustomerDTO(null, "Testsson", "testsson@test.se", "0709112233"))
                .exchange()
                .expectStatus().isOk();
        List<Customer> all = customerRepository.findAll();

        long count = all.stream().
                filter(customer -> customer.getName().equals("Testsson"))
                .count();

        assertEquals(1, count);
        assertTrue(all.getLast().getName().equals("Testsson"));
    }


    //TODO: [TEST] Dunno if fixed and broken or never finished it.
    @Test
    //TODO [TEST] Test 400 if customer has booking and 200 if no booking
    @Transactional
    void deleteByIdTest() {
        List<Customer> customers = customerRepository.findAll();
        Customer customer = customers.get(0);

        restTestClient.delete()
                .uri("http://localhost:8080/customers/delete/" + customer.getId())
                .exchange()
                .expectStatus().isEqualTo(400);

        customers = customerRepository.findAll();

        assertTrue(customers.contains(customer));

        bookingRepository.deleteBookingByCustomer(customer);

        restTestClient.delete()
                .uri("http://localhost:8080/customers/delete/" + customer.getId())
                .exchange()
                .expectStatus().isOk();

        customers = customerRepository.findAll();
        assertFalse(customers.contains(customer));
    }
}