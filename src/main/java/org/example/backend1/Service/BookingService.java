package org.example.backend1.Service;

import org.example.backend1.Model.Booking;
import org.example.backend1.Model.Customer;
import org.example.backend1.Model.Room;
import org.example.backend1.Repository.BookingRepository;
import org.example.backend1.Repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class BookingService {

    private final BookingRepository bookingRepo;
    private final RoomRepository roomRepo;


    public BookingService(BookingRepository bookingRepo, RoomRepository roomRepo) {
        this.bookingRepo = bookingRepo;
        this.roomRepo = roomRepo;
    }


    //Metod som finner alla tillgängliga och giltiga rum beroende på datum och önskade antal sängar
    public List<Room> canBook(String startDate, String endDate, boolean doubleRoom) {

        LocalDate requestedStartDate = LocalDate.parse(startDate);
        LocalDate requestedEndDate = LocalDate.parse(endDate);

        //Kollar att datum är valid, slutdatum får inte vara innan startdatum
        //Returnerar null om inkorrekt
        if(requestedStartDate.isAfter(requestedEndDate)){
            return null;
        }

        //Sparar alla bookings och alla rum i separata listor
        List<Booking> bookings = bookingRepo.findAll();
        List<Room> validRooms = roomRepo.findAll();

        //Kollar igenom alla bookings och kollar att ingen konflikt sker
        for (Booking booking : bookings) {
            boolean noConflict = booking.getEndDate().isBefore(requestedStartDate)
                    || booking.getStartDate().isAfter(requestedEndDate);

            if (noConflict) {

                //Om konflikt inträffar tar vi bort rum objektet från listan med alla rum
            } else {
                validRooms.remove(booking.getRoom());
            }
        }

        //Tar bort alla rum som antingen är dubbelrum eller enkel beroende på input
        validRooms.removeIf(room -> room.isDoubleRoom() != doubleRoom);


        //Det som består och returnerar är bara tillgängliga rum med önskade antal sängar
        return validRooms;
    }

    public Booking createBooking(String startDate, String endDate, boolean isDoubleRoom, Customer customer) {
        List<Room> availableRooms = canBook(startDate, endDate, isDoubleRoom);
        LocalDate requestedStartDate = LocalDate.parse(startDate);
        LocalDate requestedEndDate = LocalDate.parse(endDate);

        Booking currentBooking = new Booking(availableRooms.getFirst(), customer, requestedStartDate, requestedEndDate);
        bookingRepo.save(currentBooking);
        return currentBooking;
    }

    public Booking editBooking(Long bookingID, String startDate, String endDate){

        LocalDate requestedStartDate = LocalDate.parse(startDate);
        LocalDate requestedEndDate = LocalDate.parse(endDate);

        boolean available = true;

        List<Booking> bookings = bookingRepo.findAll();

        Booking currentBooking = bookingRepo.findAll().stream().filter(booking -> Objects.equals(booking.getId(), bookingID)).findAny().orElse(null);
        bookings.remove(currentBooking);

        for (Booking booking : bookings) {
            boolean noConflict = booking.getEndDate().isBefore(requestedStartDate)
                    || booking.getStartDate().isAfter(requestedEndDate);

            if (noConflict) {

            } else {
                if(currentBooking.getRoom().getId()==booking.getRoom().getId()){
                available = false;
                }
            }
        }
        if (available){
            currentBooking.setStartDate(requestedStartDate);
            currentBooking.setEndDate(requestedEndDate);
        }

        bookingRepo.save(currentBooking);
    return currentBooking;
    }


    public List<Booking> removeBooking(Long bookingID){
        bookingRepo.deleteById(bookingID);
        return bookingRepo.findAll();
    }

    public List<Booking> getAllBookings(){
        return bookingRepo.findAll();
    }

}
