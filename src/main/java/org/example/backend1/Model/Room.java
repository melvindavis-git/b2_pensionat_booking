package org.example.backend1.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String nr;
    protected boolean occupied;
    protected Long customerId;

    public Room (String nr, boolean occupied, Long customerId) {
        this.nr = nr;
        this.occupied = occupied;
        this.customerId = customerId;
    }

}
