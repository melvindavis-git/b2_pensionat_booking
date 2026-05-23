package org.example.backend1.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JacksonComponentModule;

public class BookingControllerTest extends BaseControllerTest {

    @Autowired
    private JacksonComponentModule jsonComponentModule;

    @Test
    void canBook() {

    }

    @Test
    void removeBooking() {
    }

    @Test
    void bookRoom() {
    }

    @Test
    void bookRoom2() {
    }

    @Test
    void editBooking() {
    }

    @Test
    void getAllBookings() {
        restTestClient.get()
                .uri("http://localhost:8080/rooms/getAllBookings")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].room.nr").isEqualTo("A1")
                .jsonPath("$[0].customer.name").isEqualTo("Mikael")
                .jsonPath("$[1].room.nr").isEqualTo("A2")
                .jsonPath("$[1].customer.name").isEqualTo("Tim")
                .jsonPath("$[2].room.nr").isEqualTo("B1")
                .jsonPath("$[2].customer.name").isEqualTo("Melvin");
    }
}
