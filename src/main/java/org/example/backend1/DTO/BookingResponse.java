package org.example.backend1.DTO;

import org.example.backend1.Model.Customer;
import org.example.backend1.Model.Room;

import java.time.LocalDate;

public record BookingResponse(LocalDate startDate, LocalDate endDate, Room room, Customer customer) {

}
