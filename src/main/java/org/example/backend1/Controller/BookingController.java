package org.example.backend1.Controller;

import org.example.backend1.DTO.BookingDTO;
import org.example.backend1.DTO.RoomDTO;
import org.example.backend1.Service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingController {

    private final BookingService bookingService;
    private static final Logger log = LoggerFactory.getLogger(BookingController.class);


    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    @GetMapping("rooms/canbook/{date1}/{date2}/{doubleRoom}")
    public List<RoomDTO> canBook(@PathVariable String date1, @PathVariable String date2, @PathVariable boolean doubleRoom) {
        log.info("GET request for available rooms between {} and {}", date1, date2);
        List<RoomDTO> rooms = bookingService.canBook(date1, date2, doubleRoom);
        log.info("Returned {} rooms", rooms.size());
        return rooms;
    }

    @DeleteMapping("/rooms/removebooking/{bookingID}")
    public List<BookingDTO> removeBooking(@PathVariable Long bookingID){
        log.info("DELETE request to delete booking");
        List<BookingDTO> bookings = bookingService.removeBooking(bookingID);
        log.info("Successfully removed booking with id {}", bookingID);
        return bookings;
    }

    @GetMapping("rooms/book/{startDate}/{endDate}/{isDoubleRoom}/{customerid}")
    public BookingDTO bookRoom(@PathVariable String startDate, @PathVariable String endDate,
                               @PathVariable boolean isDoubleRoom, @PathVariable Long customerid) {
       return bookingService.createBooking(startDate, endDate, isDoubleRoom, customerid);
    }


    @GetMapping("rooms/edit/{bookingID}/{startDate}/{endDate}")
    public BookingDTO editBooking(@PathVariable Long bookingID, @PathVariable String startDate, @PathVariable String endDate){
        return bookingService.editBooking(bookingID, startDate, endDate);
    }

    @GetMapping("rooms/getAllBookings")
    public List<BookingDTO> getAllBookings(){
    return bookingService.getAllBookings();
    }
}
