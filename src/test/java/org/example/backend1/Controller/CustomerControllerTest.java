package org.example.backend1.Controller;


import org.example.backend1.DTO.CustomerDTO;
import org.example.backend1.Model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CustomerControllerTest extends BaseControllerTest {


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
        assertTrue(all.getLast().getName().equals("Testsson"));
    }


    //TODO: [TEST] Dunno if fixed and broken or never finished it.
    @Test
    void deleteByIdTest() {
        List<Customer> customers = customerRepository.findAll();
        Long idBeforeDelete = customers.get(0).getId();
        restTestClient.delete()
                .uri("http://localhost:8080/customers/delete/" + idBeforeDelete)
                .exchange()
                .expectStatus().isOk();

        customers = customerRepository.findAll();
        assertFalse(customers.contains(idBeforeDelete));
    }
}