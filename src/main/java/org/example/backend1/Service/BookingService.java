package org.example.backend1.Service;

import org.example.backend1.DTO.BookingDTO;
import org.example.backend1.DTO.RoomDTO;
import org.example.backend1.Model.Booking;
import org.example.backend1.Model.Customer;
import org.example.backend1.Model.Room;
import org.example.backend1.Repository.BookingRepository;
import org.example.backend1.Repository.CustomerRepository;
import org.example.backend1.Repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

@Service
public class BookingService {

    private final BookingRepository bookingRepo;
    private final RoomRepository roomRepo;
    private final CustomerRepository customerRepository;


    public BookingService(BookingRepository bookingRepo, RoomRepository roomRepo, CustomerRepository customerRepository) {
        this.bookingRepo = bookingRepo;
        this.roomRepo = roomRepo;
        this.customerRepository = customerRepository;
    }

    public List<BookingDTO> getAllBookings() {
        return bookingRepo.findAll().stream().map(b -> BookingToBookingDTO(b)).toList();
    }

    public BookingDTO BookingToBookingDTO(Booking b) {
        return BookingDTO.builder().id(b.getId()).room
                        (new Room(b.getRoom().getId(), b.getRoom().getNr(), b.getRoom().isDoubleRoom())).customer
                        (new Customer(b.getCustomer().getId(), b.getCustomer().getName(), b.getCustomer().getEmail(),
                                b.getCustomer().getPhone())).startDate(b.getStartDate().toString())
                .endDate(b.getEndDate().toString()).build();
    }


    //Metod som finner alla tillgängliga och giltiga rum beroende på datum och önskade antal sängar
    public List<RoomDTO> canBook(String startDate, String endDate, boolean doubleRoom) {

        if(!canParseDate(startDate)&&!canParseDate(endDate)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Felaktig datum syntax");
        }

        LocalDate requestedStartDate = LocalDate.parse(startDate);
        LocalDate requestedEndDate = LocalDate.parse(endDate);

        //Kollar att datum är valid, slutdatum får inte vara innan startdatum
        if (requestedEndDate.isBefore(requestedStartDate)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Slutdatum kan inte vara innan startdatum."
            );
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
        return validRooms.stream()
                .map(room -> RoomDTO.builder()
                        .id(room.getId())
                        .nr(room.getNr())
                        .isDoubleRoom(room.isDoubleRoom())
                        .build())
                .toList();
    }


    //Skapa bokning
    public BookingDTO createBooking(String startDate, String endDate, boolean isDoubleRoom, Long customerId) {

        if(!canParseDate(startDate)&&!canParseDate(endDate)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Felaktig datum syntax");
        }


        List<RoomDTO> availableRooms = canBook(startDate, endDate, isDoubleRoom);
        LocalDate requestedStartDate = LocalDate.parse(startDate);
        LocalDate requestedEndDate = LocalDate.parse(endDate);


        if (availableRooms == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Felaktig datum input."
            );
        }

        if (availableRooms.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Inga rum tillgängliga."
            );
        }


        Room room = roomRepo.findById(availableRooms.getFirst().getId()).orElse(null);
        Customer currentCustomer = customerRepository.findById(customerId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kunden hittades inte."));


        Booking currentBooking = new Booking(room, currentCustomer, requestedStartDate, requestedEndDate);

        bookingRepo.save(currentBooking);
        return BookingToBookingDTO(currentBooking);
    }


    //Redigera bokning
    public BookingDTO editBooking(Long bookingID, String startDate, String endDate) {

        if(!canParseDate(startDate)&&!canParseDate(endDate)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Felaktig datum syntax");
        }

        LocalDate requestedStartDate = LocalDate.parse(startDate);
        LocalDate requestedEndDate = LocalDate.parse(endDate);

        if (requestedEndDate.isBefore(requestedStartDate)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Slutdatum kan inte vara innan startdatum"
            );
        }

        //Kommer hålla koll på om det rummet kan byta till angivet datum
        boolean available = true;

        //Alla bokningar
        List<Booking> bookings = bookingRepo.findAll();

        //Finner bokningen kunden inmatar via ID
        //DETTA KAN GE NULL OM MAN MATAR IN FELAKTIGT ID
        Booking currentBooking = bookingRepo.findAll().stream().filter(booking -> Objects.equals(booking.getId(), bookingID)).findAny().orElse(null);

        //Tar bort bokningen från listan alla bokningar
        //Kollen som letar efter konflikter använder denna lista
        //Om denna bokningen hade varit kvar hade den kunnat krockat i sig själv om datumen var i konflikt
        bookings.remove(currentBooking);

        //Kollar om det finns någon konflikt bland bokningar på detta rum
        for (Booking booking : bookings) {
            boolean noConflict = booking.getEndDate().isBefore(requestedStartDate)
                    || booking.getStartDate().isAfter(requestedEndDate);


            if (noConflict) {

            } else {
                //Om konflikt sker ser boolean till att det inte går
                if (currentBooking.getRoom().getId() == booking.getRoom().getId()) {
                    available = false;
                }
            }
        }
        //Om boolean ok ändra booking
        if (available) {
            currentBooking.setStartDate(requestedStartDate);
            currentBooking.setEndDate(requestedEndDate);
        }
        //Spara om bokning antingen med nya datum eller som den var
        bookingRepo.save(currentBooking);

        return BookingToBookingDTO(currentBooking);
    }


    public BookingDTO removeBooking(Long bookingID) {
        Booking deletedBooking = bookingRepo.findById(bookingID).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bokningen hittades ej."));
        BookingDTO deletedBookingDTO = BookingToBookingDTO(deletedBooking);
        bookingRepo.deleteById(bookingID);
        return deletedBookingDTO;
    }

    public boolean canParseDate(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }


}
