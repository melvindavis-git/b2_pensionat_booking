package org.example.backend1.DTO;
import lombok.Data;


@Data
public class RegisterRequest {

    private String name;
    private String email;
    private String phone;
}