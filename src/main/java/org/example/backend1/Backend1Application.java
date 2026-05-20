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
            //DATES
            LocalDate d1 = LocalDate.of(2026, 5, 18);
            LocalDate d2 = LocalDate.of(2026, 5, 20);
            LocalDate d3 = LocalDate.of(2026, 6, 18);
            LocalDate d4 = LocalDate.of(2026, 6, 20);

            //ROOMS
            Room r1 = roomRepo.save(new Room("1", true));
            Room r2 = roomRepo.save(new Room("2", true));
            roomRepo.save(new Room("3", true));
            roomRepo.save(new Room("4", true));
            roomRepo.save(new Room("5", true));
            roomRepo.save(new Room("6", false));
            roomRepo.save(new Room("7", false));
            roomRepo.save(new Room("8", false));
            roomRepo.save(new Room("9", false));
            roomRepo.save(new Room("10", false));

            //CUSTOMERS
            Customer c1 = customerRepo.save(new Customer("Melvin", "melvin@gmail.com", "070123456789"));
            customerRepo.save(new Customer("Tungvall", "tungvall@gmail.com", "070123456789"));
            customerRepo.save(new Customer("Tim", "tim@gmail.com", "070123456789"));

            //BOOKINGS
            bookingRepo.save(new Booking(r1, c1, d1, d2));
            bookingRepo.save(new Booking(r1, c1, d3, d4));
        };
    }

    //TODO: DTO-klasser (service lagret, från entity till DTO) ❌
    //TODO: låt controllers hantera thymeleaf-mallar ❌
    //TODO: ta bort kund (bara om bokningar ej finns) ✅
    //TODO: ett rum kan bokas av kund (+en eller fler nätter, datum) ❌ Måste göras i thymeleaf
    //TODO: avboka ett rum och ändra bokning ✅
    //TODO: ett rum kan vara enkel- eller dubbelrum ✅
    //TODO: dubbelrum kan ha fler sängar (1-2) ✅
    //TODO: söka på datum/datumintervall och antal personer - få fram alla lediga rum ✅
    //TODO: ha vettiga felmeddelanden, använd validerings-annoteringar i modell-klasser ❌
    //TODO: enhetstester ❌
    //TODO: Hantera fel för inga tillgängliga rum



}
