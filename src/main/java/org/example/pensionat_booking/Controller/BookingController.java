package org.example.pensionat_booking.Controller;

import org.apache.coyote.Response;
import org.example.pensionat_booking.DTO.BookingDTO;
import org.example.pensionat_booking.DTO.RoomDTO;
import org.example.pensionat_booking.Model.Booking;
import org.example.pensionat_booking.Service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        try {
            return ResponseEntity.ok(bookingService.getAllBookings());
        } catch (QueryTimeoutException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).build();
        }
    }

    @GetMapping("/available-rooms")
    public ResponseEntity<?> canBook(@RequestParam String startDate, @RequestParam String endDate, @RequestParam boolean doubleRoom) {
        try {
            return ResponseEntity.ok(bookingService.canBook(startDate, endDate, doubleRoom));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (QueryTimeoutException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).build();
        }
    }

    @DeleteMapping("/{bookingID}")
    public ResponseEntity<BookingDTO> removeBooking(@PathVariable Long bookingID) {
        log.info("DELETE request to delete booking");
        try {
            bookingService.removeBooking(bookingID);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (QueryTimeoutException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).build();
        }
    }

    @PostMapping()
    public ResponseEntity<BookingDTO> bookRoom(@RequestParam String startDate, @RequestParam String endDate,
                                               @RequestParam boolean isDoubleRoom, @RequestParam Long customerId,
                                               @RequestParam(defaultValue = "0") int extraBeds) {
        try {
            BookingDTO bookingDTO = bookingService.createBooking(startDate, endDate, isDoubleRoom, customerId, extraBeds);
            return ResponseEntity.ok(bookingDTO);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (QueryTimeoutException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).build();
        }
    }

    @PutMapping("/{id}")
    public BookingDTO editBooking(@PathVariable Long bookingID, @PathVariable String
            startDate, @PathVariable String endDate) {
        return bookingService.editBooking(bookingID, startDate, endDate);
    }

    @GetMapping("/customer/{customerId}/exists")
    public boolean customerHasBookings(@PathVariable Long customerId) {
        return bookingService.customerHasBookings(customerId);
    }
}
