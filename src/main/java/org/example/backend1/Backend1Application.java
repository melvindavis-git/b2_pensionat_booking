package org.example.backend1;

import org.example.backend1.Model.Booking;
import org.example.backend1.Model.Customer;
import org.example.backend1.Model.Room;
import org.example.backend1.Repository.BookingRepository;
import org.example.backend1.Repository.CustomerRepository;
import org.example.backend1.Repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class Backend1Application {

    public static void main(String[] args) {
        SpringApplication.run(Backend1Application.class, args);
    }

    @Bean
    public CommandLineRunner createRooms(RoomRepository roomRepo, CustomerRepository customerRepo, BookingRepository bookingRepo) {
        return (args) -> {
            LocalDate d1 = LocalDate.of(2026, 5, 18);
            LocalDate d2 = LocalDate.of(2026, 5, 20);
            Room r1 = roomRepo.save(new Room("1"));
            roomRepo.save(r1);
            roomRepo.save(new Room("2"));
            roomRepo.save(new Room("3"));
            roomRepo.save(new Room("4"));
            roomRepo.save(new Room("5"));
            roomRepo.save(new Room("6"));
            roomRepo.save(new Room("7"));
            roomRepo.save(new Room("8"));
            roomRepo.save(new Room("9"));
            roomRepo.save(new Room("10"));


            Customer c1 = customerRepo.save(new Customer("Melvin", "melvin@gmail.com", "070123456789"));
            customerRepo.save(new Customer("Tungvall", "tungvall@gmail.com", "070123456789"));
            customerRepo.save(new Customer("Tim", "tim@gmail.com", "070123456789"));

            bookingRepo.save(new Booking(r1, c1, d1, d2));
        };
    }

    //TODO: DTO-klasser (service lagret, från entity till DTO)
    //TODO: låt controllers hantera thymeleaf-mallar
    //TODO: ta bort kund (bara om bokningar ej finns)
    //TODO: ett rum kan bokas av kund (+en eller fler nätter, datum)
    //TODO: avboka ett rum och ändra bokning
    //TODO: ett rum kan vara enkel- eller dubbelrum
    //TODO: dubbelrum kan ha fler sängar (1-2)
    //TODO: söka på datum/datumintervall och antal personer - få fram alla lediga rum
    //TODO: ha vettiga felmeddelanden, använd validerings-annoteringar i modell-klasser
    //TODO: enhetstester


}
