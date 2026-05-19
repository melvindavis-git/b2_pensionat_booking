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
        List<Booking> bookings = repo.findAll();
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

}
