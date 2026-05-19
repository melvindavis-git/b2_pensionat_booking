package org.example.backend1.Controller;

import org.example.backend1.Model.Booking;
import org.example.backend1.Model.Customer;
import org.example.backend1.Model.Room;
import org.example.backend1.Service.BookingService;
import org.example.backend1.Service.RoomService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class BookingController {

    private final BookingService bookingService;
    private final RoomService roomService;

    public BookingController(BookingService bookingService, RoomService roomService) {
        this.bookingService = bookingService;
        this.roomService = roomService;
    }


    @GetMapping("rooms/canbook/{date1}/{date2}/{doubleRoom}")
    public List<Room> canBook(@PathVariable String date1, @PathVariable String date2, @PathVariable boolean doubleRoom) {
        return bookingService.canBook(date1, date2, doubleRoom);
    }

    @DeleteMapping("/rooms/removebooking/{bookingID}")
    public List<Booking> removeBooking(@PathVariable Long bookingID){
        return bookingService.removeBooking(bookingID);
    }

    @GetMapping("rooms/book/{startDate}/{endDate}/{isDoubleRoom}/{customer}")
    public Booking bookRoom(@PathVariable String startDate, @PathVariable String endDate,
                         @PathVariable boolean isDoubleRoom, @PathVariable Customer customer) {
       return bookingService.createBooking(startDate, endDate, isDoubleRoom, customer);
    }


    @GetMapping("rooms/edit/{bookingID}/{startDate}/{endDate}")
    public Booking editBooking(@PathVariable Long bookingID, @PathVariable String startDate, @PathVariable String endDate){
        return bookingService.editBooking(bookingID, startDate, endDate);
    }

    @GetMapping("rooms/getAllBookings")
    public List<Booking> getAllBookings(){
    return bookingService.getAllBookings();
    }
}
