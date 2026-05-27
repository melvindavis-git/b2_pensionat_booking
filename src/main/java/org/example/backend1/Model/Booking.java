package org.example.backend1.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    protected Room room;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    protected Customer customer;


    protected LocalDate startDate;


    protected LocalDate endDate;

    protected int extraBeds;

    public Booking(Room room, Customer customer, LocalDate startDate, LocalDate endDate) {
        this.room = room;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.extraBeds = 0;
    }


}
