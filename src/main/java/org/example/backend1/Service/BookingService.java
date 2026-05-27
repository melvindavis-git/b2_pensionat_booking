package org.example.backend1.Service;

import org.example.backend1.DTO.BookingDTO;
import org.example.backend1.DTO.RoomDTO;
import org.example.backend1.Model.Booking;
import org.example.backend1.Model.Customer;
import org.example.backend1.Model.Room;
import org.example.backend1.Repository.BookingRepository;
import org.example.backend1.Repository.CustomerRepository;
import org.example.backend1.Repository.RoomRepository;
import org.springframework.stereotype.Service;

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

                .endDate(b.getEndDate().toString()).extraBeds(b.getExtraBeds()).build();
    }

    public List<RoomDTO> canBook(String startDate, String endDate, boolean doubleRoom) {

        if (!canParseDate(startDate)) {
            throw new RuntimeException(
                    "Måste ange startdatum.");
        }

        if (!canParseDate(endDate)) {
            throw new RuntimeException(
                    "Måste ange slutdatum.");
        }

        LocalDate requestedStartDate = LocalDate.parse(startDate);
        LocalDate requestedEndDate = LocalDate.parse(endDate);

        if (requestedEndDate.isBefore(requestedStartDate)) {
            throw new RuntimeException("Slutdatum kan inte vara innan startdatum.");
        }

        List<Booking> bookings = bookingRepo.findAll();
        List<Room> validRooms = roomRepo.findAll();

        for (Booking booking : bookings) {
            boolean noConflict = booking.getEndDate().isBefore(requestedStartDate)
                    || booking.getStartDate().isAfter(requestedEndDate);

            if (noConflict) {

            } else {
                validRooms.remove(booking.getRoom());
            }
        }

        validRooms.removeIf(room -> room.isDoubleRoom() != doubleRoom);

        return validRooms.stream()
                .map(room -> RoomDTO.builder()
                        .id(room.getId())
                        .nr(room.getNr())
                        .isDoubleRoom(room.isDoubleRoom())
                        .build())
                .toList();
    }

    public BookingDTO createBooking(String startDate, String endDate, boolean isDoubleRoom, Long customerId, int extraBeds) {

        if (!canParseDate(startDate) && !canParseDate(endDate)) {
            throw new RuntimeException("Måste ange både slut och startdatum.");
        }


        List<RoomDTO> availableRooms = canBook(startDate, endDate, isDoubleRoom);
        LocalDate requestedStartDate = LocalDate.parse(startDate);
        LocalDate requestedEndDate = LocalDate.parse(endDate);


        if (availableRooms == null) {
            throw new RuntimeException("Felaktig datum input.");
        }

        if (availableRooms.isEmpty()) {
            throw new RuntimeException("Inga rum tillgängliga.");
        }


        Room room = roomRepo.findById(availableRooms.getFirst().getId()).orElse(null);
        Customer currentCustomer = customerRepository.findById(customerId).orElseThrow(() ->
                new RuntimeException("Kunden hittades inte."));


        Booking currentBooking = new Booking(room, currentCustomer, requestedStartDate, requestedEndDate);
        currentBooking.setExtraBeds(extraBeds);
        bookingRepo.save(currentBooking);
        return BookingToBookingDTO(currentBooking);
    }

    public BookingDTO editBooking(Long bookingID, String startDate, String endDate) {

        if (!canParseDate(startDate) && !canParseDate(endDate)) {
            throw new RuntimeException("Felaktig datum syntax.");
        }

        LocalDate requestedStartDate = LocalDate.parse(startDate);
        LocalDate requestedEndDate = LocalDate.parse(endDate);

        if (requestedEndDate.isBefore(requestedStartDate)) {
            throw new RuntimeException("Slutdatum kan inte vara innan startdatum.");
        }

        boolean available = true;

        List<Booking> bookings = bookingRepo.findAll();

        Booking currentBooking = bookingRepo.findAll().stream().filter(booking -> Objects.equals(booking.getId(), bookingID)).findAny().orElse(null);

        bookings.remove(currentBooking);

        for (Booking booking : bookings) {
            boolean noConflict = booking.getEndDate().isBefore(requestedStartDate)
                    || booking.getStartDate().isAfter(requestedEndDate);

            if (noConflict) {

            } else {
                if (currentBooking.getRoom().getId() == booking.getRoom().getId()) {
                    available = false;
                }
            }
        }
        if (available) {
            currentBooking.setStartDate(requestedStartDate);
            currentBooking.setEndDate(requestedEndDate);
        }
        bookingRepo.save(currentBooking);

        return BookingToBookingDTO(currentBooking);
    }


    public BookingDTO removeBooking(Long bookingID) {
        Booking deletedBooking = bookingRepo.findById(bookingID).orElseThrow(() ->
                new RuntimeException("Bokningen hittades ej."));
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

    public BookingDTO editById(Long customerId, String startDate, String endDate) {
        Booking editedBooking = bookingRepo.findById(customerId).orElseThrow(() -> new RuntimeException("Bokningen hittades ej"));

        LocalDate requestedStartDate = LocalDate.parse(startDate);
        LocalDate requestedEndDate = LocalDate.parse(endDate);

        if (requestedEndDate.isBefore(requestedStartDate)) {
            throw new RuntimeException("Slutdatum kan inte vara innan startdatum.");
        }

        editedBooking.setStartDate(requestedStartDate);
        editedBooking.setEndDate(requestedEndDate);

        bookingRepo.save(editedBooking);
        return BookingToBookingDTO(editedBooking);
    }

    public BookingDTO getBookingById(Long id) {
        return BookingToBookingDTO(Objects.requireNonNull(bookingRepo.findById(id).orElse(null)));
    }

    public boolean canAddBeds(Long id, int extraBeds) {

        if (extraBeds < 1) {
            throw new RuntimeException("För få sängar.");
        } else {
            if (extraBeds > 3) {
                throw new RuntimeException("För många sängar.");
            }
        }

        BookingDTO currentBooking = getBookingById(id);
        if (currentBooking.getRoom().isDoubleRoom()) {
            currentBooking.setExtraBeds(extraBeds);
            return true;
        }

        return false;
    }


}
