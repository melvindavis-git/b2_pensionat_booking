package org.example.backend1.Repository;

import org.example.backend1.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {


}
