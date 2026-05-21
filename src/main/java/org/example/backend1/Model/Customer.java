package org.example.backend1.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Can not be blank.")
    @Size(min = 2, max = 20, message = "Name too short or too long.")
    protected String name;

    @Email(message = "Invalid email format.")
    @NotBlank(message = "Can not be blank.")
    protected String email;

    @NotBlank(message = "Can not be blank.")
    @Size(min = 2, max = 20, message = "Phonenumber too short or too long.")
    protected String phone;

    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

}
