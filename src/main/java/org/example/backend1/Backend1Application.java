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
            LocalDate d3 = LocalDate.of(2026, 6, 18);
            LocalDate d4 = LocalDate.of(2026, 6, 20);
            LocalDate d5 = LocalDate.of(2026, 8, 3);
            LocalDate d6 = LocalDate.of(2026, 8, 7);

            Room r1 = roomRepo.save(new Room("A1", true));
            Room r2 = roomRepo.save(new Room("A2", true));
            Room r3 = roomRepo.save(new Room("B3", true));
            Room r4 = roomRepo.save(new Room("B4", true));
            Room r5 = roomRepo.save(new Room("C5", true));
            Room r6 = roomRepo.save(new Room("C6", false));
            Room r7 = roomRepo.save(new Room("D7", false));
            Room r8 = roomRepo.save(new Room("D8", false));
            Room r9 = roomRepo.save(new Room("E9", false));
            Room r10 = roomRepo.save(new Room("E10", false));

            Customer c1 = customerRepo.save(new Customer("Melvin", "melvin@gmail.com", "070123456789"));
            Customer c2 = customerRepo.save(new Customer("Tungvall", "tungvall@gmail.com", "070123456789"));
            Customer c3 = customerRepo.save(new Customer("Tim", "tim@gmail.com", "070123456789"));

            bookingRepo.save(new Booking(r1, c1, d1, d2));
            bookingRepo.save(new Booking(r4, c2, d3, d4));
            bookingRepo.save(new Booking(r8, c3, d5, d6));
            bookingRepo.save(new Booking(r2, c1, d1, d2));
            bookingRepo.save(new Booking(r3, c1, d1, d2));
            bookingRepo.save(new Booking(r4, c1, d1, d2));

        };

        //TODO: DTO-klasser (service lagret, från entity till DTO) ✅
        //TODO: ta bort kund (bara om bokningar ej finns) ✅
        //TODO: ett rum kan bokas av kund (+en eller fler nätter, datum) ✅
        //TODO: avboka ett rum och ändra bokning ✅
        //TODO: ett rum kan vara enkel- eller dubbelrum ✅
        //TODO: dubbelrum kan ha fler sängar (1-2) ✅
        //TODO: söka på datum/datumintervall och antal personer - få fram alla lediga rum ✅
        //TODO: använd validerings-annoteringar i modell-klasser ✅
        //TODO: låt controllers hantera thymeleaf-mallar ✅
        //TODO: Hantera fel för inga tillgängliga rum ✅
        //TODO: ha vettiga felmeddelanden ✅
        //TODO: enhetstester ✅


    }
}
