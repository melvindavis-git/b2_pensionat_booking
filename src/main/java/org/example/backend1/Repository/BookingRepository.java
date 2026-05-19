package org.example.backend1.Repository;

import org.example.backend1.Model.Booking;
import org.example.backend1.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
}
