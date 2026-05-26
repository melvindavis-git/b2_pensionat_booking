package org.example.backend1.Controller;

import org.example.backend1.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;

class RoomControllerTest extends BaseTest {

    @AutoConfigureRestTestClient

    @Test
    void getAllRooms() {
        restTestClient.get()
                .uri("http://localhost:8080/api/rooms")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].nr").isEqualTo("A1")
                .jsonPath("$[1].nr").isEqualTo("A2")
                .jsonPath("$[2].nr").isEqualTo("A3")
                .jsonPath("$[3].nr").isEqualTo("B1")
                .jsonPath("$[0].doubleRoom").isEqualTo(false)
                .jsonPath("$[3].doubleRoom").isEqualTo(true);
    }
}
