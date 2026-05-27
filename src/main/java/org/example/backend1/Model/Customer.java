package org.example.backend1.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;


    @Size(min = 2, message = "Namn för kort.")
    @Size(max = 20, message = "Namn för Långt.")
    protected String name;

    @Email(message = "Ogiltig email.")
    @Size(min = 10, message = "Mailadress är för kort.")
    @Size(max = 40, message = "Mailadress är för lång.")
    protected String email;

    @Size(min = 2, max = 20, message = "Telefonnummer är för kort")
    @Size(min = 2, max = 20, message = "Telefonnummeret är för långt.")
    protected String phone;

    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

}
