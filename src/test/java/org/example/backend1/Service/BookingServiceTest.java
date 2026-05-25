package org.example.backend1.Service;

import org.example.backend1.Controller.BaseControllerTest;
import org.example.backend1.DTO.BookingDTO;
import org.example.backend1.Model.Booking;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class BookingServiceTest extends BaseControllerTest {

    @Autowired
    private BookingService bookingService;

    @Test
    void canBook() {
    }

    @Test
    void createBooking() {
    }

    @Test
    void createBooking2() {
    }

    @Test
    void editBooking() {
    }

    @Test
    void removeBooking() {
        Booking booking = bookingRepository.findAll().get(0);
        bookingService.removeBooking(booking.getId());
        List<Booking>bookings = bookingRepository.findAll();
        assertFalse(bookings.contains(booking.getId()));
    }

    @Test
    void getAllBookings() {
        List<BookingDTO> bookings = bookingService.getAllBookings();
        assertTrue(bookings.size() == 3);
    }
}
