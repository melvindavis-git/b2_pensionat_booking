package org.example.backend1.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
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

    protected Date bookingDate;
}
