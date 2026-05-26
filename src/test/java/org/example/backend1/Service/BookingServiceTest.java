package org.example.backend1.Service;

import org.example.backend1.BaseTest;
import org.example.backend1.DTO.BookingDTO;
import org.example.backend1.DTO.RoomDTO;
import org.example.backend1.Model.Booking;
import org.example.backend1.Model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class BookingServiceTest extends BaseTest {

    @Autowired
    private BookingService bookingService;

    @Test
    void getAllBookingsTest() {
        List<BookingDTO> bookings = bookingService.getAllBookings();
        assertTrue(bookings.size() == 3);
    }
    @Test
    void canBookTest() {
//        public List<RoomDTO> canBook(String startDate, String endDate, boolean doubleRoom) {

        List<RoomDTO> availableRooms = bookingService.canBook("2026-05-24", "2026-05-25",true );
        List<RoomDTO> availableRooms2 = bookingService.canBook("2026-05-15", "2026-05-20",false);
        List<RoomDTO> noAvailableRooms = bookingService.canBook("2026-05-15", "2026-05-20",true);

        assertTrue(availableRooms.size() == 1);
        assertTrue(availableRooms2.size() == 2);
        assertTrue(noAvailableRooms.size() == 0 );

        assertThrows(RuntimeException.class, () -> bookingService.canBook("20260515", "2026-05-20",false));
        assertThrows(RuntimeException.class, () -> bookingService.canBook("2026-05-20", "2026-05-15",true));
    }

    @Test
    void createBookingTest() {
//        public BookingDTO createBooking(String startDate, String endDate, boolean isDoubleRoom, Long customerId) {
       Customer customer = customerRepository.findAll().getFirst();
       BookingDTO bookingDTO = bookingService.createBooking("2025-06-01", "2025-06-02", false, customer.getId(), 0);
       Optional<Booking> bookings = bookingRepository.findById(bookingDTO.getId());
       assertNotNull(bookingDTO);

       assertTrue(bookingDTO.getCustomer().equals(customer));
       assertTrue(bookingDTO.getStartDate().equals("2025-06-01"));
       assertTrue(bookingDTO.getEndDate().equals("2025-06-02"));
       assertTrue(bookings.get().getId().equals(bookingDTO.getId()));
    }

    @Test
    void editBooking() {
    }

    @Test
    void removeBooking() {
        Booking booking = bookingRepository.findAll().get(0);
        bookingService.removeBooking(booking.getId());
        List<Booking> bookings = bookingRepository.findAll();
        assertFalse(bookings.contains(booking.getId()));
    }

}
