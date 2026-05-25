package org.example.backend1;

import org.example.backend1.Model.Booking;
import org.example.backend1.Model.Customer;
import org.example.backend1.Model.Room;
import org.example.backend1.Repository.BookingRepository;
import org.example.backend1.Repository.CustomerRepository;
import org.example.backend1.Repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.time.LocalDate;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureRestTestClient
public abstract class BaseTest {

    @Autowired
    protected CustomerRepository customerRepository;
    @Autowired
    protected RoomRepository roomRepository;
    @Autowired
    protected BookingRepository bookingRepository;
    @Autowired
    protected RestTestClient restTestClient;

    @BeforeEach
    void setUp() {
        bookingRepository.deleteAll();
        roomRepository.deleteAll();
        customerRepository.deleteAll();

        Customer customer1 = new Customer("Mikael", "mikael@mail.com", "070311223344");
        Customer customer2 = new Customer("Tim", "tim@mail.com", "070311223344");
        Customer customer3 = new Customer("Melvin", "melvin@mail.com", "070311223344");
        Customer customer4 = new Customer("Rolf", "rolfh@mail.com", "070311223344");

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);
        customerRepository.save(customer4);

        Room room1 = new Room("A1", false);
        Room room2 = new Room("A2", false);
        Room room3 = new Room("A3", false);
        Room room4 = new Room("B1", true);

        roomRepository.save(room1);
        roomRepository.save(room2);
        roomRepository.save(room3);
        roomRepository.save(room4);

        bookingRepository.save(new Booking(room1, customer1, LocalDate.of(2026, 05, 10), LocalDate.of(2026, 05, 20)));
        bookingRepository.save(new Booking(room4, customer2, LocalDate.of(2026, 05, 15), LocalDate.of(2026, 05, 20)));
        bookingRepository.save(new Booking(room4, customer3, LocalDate.of(2026, 05, 21), LocalDate.of(2026, 05, 22)));
    }

//    @AfterEach
//    void tearDown() {
//        bookingRepository.deleteAll();
//        roomRepository.deleteAll();
//        customerRepository.deleteAll();
//    }


}
