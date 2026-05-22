package org.example.backend1.Controller;


import org.example.backend1.BaseControllerTest;
import org.example.backend1.Repository.BookingRepository;
import org.example.backend1.Repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest
@AutoConfigureRestTestClient
class CustomerControllerTest extends BaseControllerTest {

    @Autowired
    private RestTestClient restTestClient;

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
    }
    @Test
    void deleteByIdTest() {
    }
}