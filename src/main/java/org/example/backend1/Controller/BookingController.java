package org.example.backend1.Controller;

import org.example.backend1.Model.Booking;
import org.example.backend1.Model.Room;
import org.example.backend1.Service.BookingService;
import org.example.backend1.Service.RoomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class BookingController {

    private final BookingService service;
    private final RoomService roomService;

    public BookingController(BookingService service, RoomService roomService) {
        this.service = service;
        this.roomService = roomService;
    }



    @GetMapping("rooms/canbook/{date1}/{date2}/{doubleRoom}")
    public List<Room> canBook(@PathVariable String date1, @PathVariable String date2, @PathVariable boolean doubleRoom) {
        return service.canBook(date1, date2, doubleRoom);
    }

//    @GetMapping("rooms/{roomNumber}/{customerId}")
//    public List<Booking> bookRoom(@PathVariable String roomNumber, @PathVariable Long customerId) {
//        return service.bookRoom(roomNumber, customerId);
//    }

//    @GetMapping("rooms/status")
//    public List<Booking> getBookedRooms() {
////        return service.getBookedRooms();
//
//    }

}
