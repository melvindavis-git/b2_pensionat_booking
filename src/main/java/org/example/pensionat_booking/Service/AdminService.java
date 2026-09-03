package org.example.pensionat_booking.Service;

import org.apache.coyote.Response;
import org.example.pensionat_booking.DTO.BookingDTO;
import org.example.pensionat_booking.DTO.CustomerDTO;
import org.example.pensionat_booking.Model.Booking;
import org.example.pensionat_booking.Model.Room;
import org.example.pensionat_booking.Repository.BookingRepository;
import org.example.pensionat_booking.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class AdminService {


    private final BookingRepository bookingRepository;
    private final CustomerService customerService;
    private final BookingService bookingService;
    RestTemplate restTemplate = new RestTemplate();
    String baseUrl;


    public AdminService(BookingRepository bookingRepository, CustomerService customerService, @Value("${customer-service.base-url}") String baseUrl, BookingService bookingService) {
        this.baseUrl = baseUrl;
        this.bookingRepository = bookingRepository;
        this.customerService = customerService;
        this.bookingService = bookingService;
    }

    public List<CustomerDTO> resetDatabaseDevData() {
        String status ="";

        List<CustomerDTO> notDeletedCustomers = new ArrayList<>();
        System.out.println(notDeletedCustomers.toString());

        try {
            List<String> customers = customerService.getAllCustomers();
            status = status + " Inga kunder hittades.";
            if (customers.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inga kudner hittades.");
            }
            customers.forEach(customer -> System.out.println(customer.getId() + " " + customer.getName()));
            customers.forEach(customer -> {
                List<BookingDTO> customerBookings = bookingService.getAllBookings();

                customerBookings.forEach(booking -> {
                    System.out.println(booking.getId());
                });
                if (!customerBookings.isEmpty()) {
                    customerBookings.forEach(booking -> bookingRepository.deleteById(booking.getId()));
                }

                ResponseEntity<Void> response = customerService.deleteById(customer.getId());

                if (!response.getStatusCode().is2xxSuccessful()) {
                    notDeletedCustomers.add(customer);
                }
            });

            if (notDeletedCustomers.isEmpty()) {
                return customers;
            }
            return notDeletedCustomers;

        } catch (DataAccessException e) {
            System.out.println("DEBUG: ADMIN API ENDPOINT HIT CATCH");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    public boolean createDatabaseDevData(RoomRepository roomRepo, BookingRepository bookingRepo, CustomerService customerService) {
        try {
            bookingRepo.deleteAll();
            roomRepo.deleteAll();
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

            CustomerDTO c1 = new CustomerDTO(1L, "Melvin", "melvin@gmail.com", "070123456789");
            CustomerDTO c2 = new CustomerDTO(2L, "Tungvall", "tungvall@gmail.com", "070123456789");
            CustomerDTO c3 = new CustomerDTO(3L, "Tim", "tim@gmail.com", "070123456789");
            CustomerDTO c1Response = customerService.registerCustomer(c1).getBody();
            CustomerDTO c2Response = customerService.registerCustomer(c2).getBody();
            CustomerDTO c3Response = customerService.registerCustomer(c3).getBody();

            bookingRepo.save(new Booking(r1, c1Response.getId(), d1, d2));
            bookingRepo.save(new Booking(r4, c2Response.getId(), d3, d4));
            bookingRepo.save(new Booking(r8, c3Response.getId(), d5, d6));
            bookingRepo.save(new Booking(r2, c1Response.getId(), d1, d2));
            bookingRepo.save(new Booking(r3, c1Response.getId(), d1, d2));
            bookingRepo.save(new Booking(r4, c1Response.getId(), d1, d2));
        } catch (DataAccessException de) {
           return false;

        }
        return true;
    }
}