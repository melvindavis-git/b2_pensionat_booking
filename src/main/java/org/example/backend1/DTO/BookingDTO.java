package org.example.backend1.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend1.Model.Customer;
import org.example.backend1.Model.Room;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Room room;
    private Customer customer;
    private String startDate;
    private String endDate;
    private int extraBeds;
}