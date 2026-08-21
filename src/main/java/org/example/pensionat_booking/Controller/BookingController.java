package org.example.pensionat_booking.Controller;

import org.example.pensionat_booking.DTO.BookingDTO;
import org.example.pensionat_booking.DTO.RoomDTO;
import org.example.pensionat_booking.Service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private static final Logger log = LoggerFactory.getLogger(BookingController.class);


    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping()
    public List<BookingDTO> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/available-rooms")
    public List<RoomDTO> canBook(@RequestParam String startDate, @RequestParam String endDate,
                                 @RequestParam boolean doubleRoom) {
        return bookingService.canBook(startDate, endDate, doubleRoom);
    }

    @DeleteMapping("/{bookingID}")
    public BookingDTO removeBooking(@PathVariable Long bookingID) {
        log.info("DELETE request to delete booking");
        BookingDTO removedBooking = bookingService.removeBooking(bookingID);
        log.info("Successfully removed booking with id {}", bookingID);
        return removedBooking;
    }

    @PostMapping()
    public BookingDTO bookRoom(@RequestParam String startDate, @RequestParam String endDate,
                               @RequestParam boolean isDoubleRoom, @RequestParam Long customerId) {
        return bookingService.createBooking(startDate, endDate, isDoubleRoom, customerId, 0);
    }


    @PutMapping("/{id}")
    public BookingDTO editBooking(@PathVariable Long bookingID, @PathVariable String startDate, @PathVariable String endDate) {
        return bookingService.editBooking(bookingID, startDate, endDate);
    }
}
