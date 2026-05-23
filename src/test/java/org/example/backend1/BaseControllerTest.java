package org.example.backend1;

import org.example.backend1.Model.Customer;
import org.example.backend1.Repository.BookingRepository;
import org.example.backend1.Repository.CustomerRepository;
import org.example.backend1.Repository.RoomRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseControllerTest {

    @Autowired
    protected CustomerRepository customerRepository;
    @Autowired
    protected RoomRepository roomRepository;
    @Autowired
    protected BookingRepository bookingRepository;

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

    }

//    @AfterEach
//    void tearDown() {
//        bookingRepository.deleteAll();
//        roomRepository.deleteAll();
//        customerRepository.deleteAll();
//    }


}
