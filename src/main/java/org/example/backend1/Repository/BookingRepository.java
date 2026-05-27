package org.example.backend1.Repository;

import org.example.backend1.Model.Booking;
import org.example.backend1.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    void deleteBookingByCustomer(Customer customer);
}
