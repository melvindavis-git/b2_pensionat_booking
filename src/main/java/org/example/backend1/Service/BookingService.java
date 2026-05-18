package org.example.backend1.Service;

import org.example.backend1.Model.Booking;
import org.example.backend1.Model.Room;
import org.example.backend1.Repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository repo;

    public BookingService(BookingRepository repo) {
        this.repo = repo;
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
        //List<Booking> roomBookings = bookings.stream().filter(booking -> booking.getRoom().getNr().equals(nr)).toList();
        List<Room> validRooms = null;


        for (Booking booking : bookings) {
            if (booking.getStartDate().isAfter(requestedStartDate) && booking.getEndDate().isAfter(requestedStartDate)
                    || booking.getStartDate().isBefore(requestedStartDate) && booking.getEndDate().isBefore(requestedStartDate)) {
            } else {

            }
            if (booking.getStartDate().isAfter(requestedEndDate) && booking.getEndDate().isAfter(requestedEndDate)
                    || booking.getStartDate().isBefore(requestedEndDate) && booking.getEndDate().isBefore(requestedEndDate)) {
                validRooms.add(booking.getRoom());
            }
        }


        return validRooms;
    }




//    public List<Date> getBookedRooms() {
//        return repo.getBookingByStartDate();
//    }
}
