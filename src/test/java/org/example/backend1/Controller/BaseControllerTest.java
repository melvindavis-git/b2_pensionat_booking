package org.example.backend1.Controller;

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

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureRestTestClient
public abstract class BaseControllerTest {

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

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        Room room1 = new Room("1A", false);
        Room room2 = new Room("2A", false);
        Room room3 = new Room("3A", false);
        Room room4 = new Room("1B", true);

        roomRepository.save(room1);
        roomRepository.save(room2);
        roomRepository.save(room3);
        roomRepository.save(room4);

    }

//    @AfterEach
//    void tearDown() {
//        bookingRepository.deleteAll();
//        roomRepository.deleteAll();
//        customerRepository.deleteAll();
//    }


}
