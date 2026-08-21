package org.example.pensionat_booking.Repository;

import org.example.pensionat_booking.Model.Booking;
import org.example.pensionat_booking.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    void deleteBookingByCustomer(Customer customer);
}
