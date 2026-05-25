package org.example.backend1.Controller;

import org.example.backend1.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;

class RoomControllerTest extends BaseTest {

    @AutoConfigureRestTestClient

    @Test
    void getAllRooms() {
        restTestClient.get()
                .uri("http://localhost:8080/rooms")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].nr").isEqualTo("1A")
                .jsonPath("$[1].nr").isEqualTo("2A")
                .jsonPath("$[2].nr").isEqualTo("3A")
                .jsonPath("$[3].nr").isEqualTo("1B")
                .jsonPath("$[0].doubleRoom").isEqualTo(false)
                .jsonPath("$[3].doubleRoom").isEqualTo(true);
    }
}
