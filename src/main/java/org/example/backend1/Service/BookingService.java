package org.example.backend1.Service;

import org.example.backend1.Model.Booking;
import org.example.backend1.Model.Room;
import org.example.backend1.Repository.BookingRepository;
import org.example.backend1.Repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository repo;
    private final RoomRepository roomRepo;


    public BookingService(BookingRepository repo, RoomRepository roomRepo) {
        this.repo = repo;
        this.roomRepo = roomRepo;
    }


//    public List<Booking> isBooked(String roomNumber, Date startDate) {
//        List<Booking> bookings = repo.findBookingByRoom_Nr(roomNumber);
//        bookings.stream().filter(booking -> booking.getStartDate().before(startDate)
//                && booking.getEndDate().after(startDate)).forEach(booking -> {});
//
//        return bookings;
//    }


    public List<Room> canBook(String startDate, String endDate) {

        LocalDate requestedStartDate = LocalDate.parse(startDate);
        LocalDate requestedEndDate = LocalDate.parse(endDate);

        List<Booking> bookings = repo.findAll();
        List<Room> validRooms = roomRepo.findAll();


        for (Booking booking : bookings) {
            boolean noConflict = booking.getEndDate().isBefore(requestedStartDate)
                    || booking.getStartDate().isAfter(requestedEndDate);
            if (noConflict) {

            } else {
                validRooms.remove(booking.getRoom());
            }
        }


        return validRooms;
    }




//    public List<Date> getBookedRooms() {
//        return repo.getBookingByStartDate();
//    }
}
