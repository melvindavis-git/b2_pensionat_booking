package org.example.pensionat_booking;

import org.example.pensionat_booking.Model.Booking;
import org.example.pensionat_booking.Model.Room;
import org.example.pensionat_booking.Repository.BookingRepository;
import org.example.pensionat_booking.Repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.time.LocalDate;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureRestTestClient
public abstract class BaseTest {
    @Autowired
    protected RoomRepository roomRepository;
    @Autowired
    protected BookingRepository bookingRepository;
    @Autowired
    protected RestTestClient restTestClient;

    @BeforeEach
    void setUp() {
        bookingRepository.deleteAll();
        roomRepository.deleteAll();

        Room room1 = new Room("A1", false);
        Room room2 = new Room("A2", false);
        Room room3 = new Room("A3", false);
        Room room4 = new Room("B1", true);

        roomRepository.save(room1);
        roomRepository.save(room2);
        roomRepository.save(room3);
        roomRepository.save(room4);

        bookingRepository.save(new Booking(room1, 1L, LocalDate.of(2026, 05, 10), LocalDate.of(2026, 05, 20)));
        bookingRepository.save(new Booking(room4, 2L, LocalDate.of(2026, 05, 15), LocalDate.of(2026, 05, 20)));
        bookingRepository.save(new Booking(room4, 3L, LocalDate.of(2026, 05, 21), LocalDate.of(2026, 05, 22)));
    }


}
