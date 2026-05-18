package org.example.backend1.Repository;

import org.example.backend1.Model.Booking;
import org.example.backend1.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    

    List<Date> getBookingByStartDate(Date date);

    List<Booking> findBookingByRoom_Nr(String roomNr);
}
