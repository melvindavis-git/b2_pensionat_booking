package org.example.pensionat_booking.Controller;

import org.example.pensionat_booking.BaseTest;
import org.junit.jupiter.api.Test;

public class BookingControllerTest extends BaseTest {

    @Test
    void getAllBookings() {
        restTestClient.get()
                .uri("http://localhost:8080/api/bookings")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].room.nr").isEqualTo("A1")
                .jsonPath("$[0].customer.name").isEqualTo("Mikael")
                .jsonPath("$[1].room.nr").isEqualTo("B1")
                .jsonPath("$[1].customer.name").isEqualTo("Tim")
                .jsonPath("$[2].room.nr").isEqualTo("B1")
                .jsonPath("$[2].customer.name").isEqualTo("Melvin");
    }
}
