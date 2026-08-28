package org.example.pensionat_booking.Service;

import org.example.pensionat_booking.DTO.CustomerDTO;
import org.example.pensionat_booking.Model.Booking;
import org.example.pensionat_booking.Model.Customer;
import org.example.pensionat_booking.Repository.BookingRepository;
import org.example.pensionat_booking.Repository.CustomerRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerService {

    private final CustomerRepository customerRepo;
    private final BookingRepository bookingRepo;
    RestTemplate restTemplate = new RestTemplate();

    public CustomerService(CustomerRepository customerRepo, BookingRepository bookingRepo, BookingRepository bookingRepo1) {
        this.customerRepo = customerRepo;
        this.bookingRepo = bookingRepo;
    }

    public List<CustomerDTO> getAllCustomers() {
        return restTemplate.getForObject("http://localhost:8081/customers/all", List.class);
    }

    public CustomerDTO CustomerToCustomerDTO(Customer c) {
        return CustomerDTO.builder().id(c.getId()).name(c.getName()).email(c.getEmail()).phone(c.getPhone()).build();
    }


    public ResponseEntity<CustomerDTO> registerCustomer(CustomerDTO inputCustomer) {

        try{
            CustomerDTO savedCst = restTemplate.postForObject("http://localhost:8081/customers/register", inputCustomer, CustomerDTO.class);
            return ResponseEntity.ok(savedCst);
        }
        catch (Exception e){
            System.out.println(e);
            return ResponseEntity.internalServerError().build();
        }
    }

    public boolean deleteById(Long customerId) {

        for (Booking booking : bookingRepo.findAll()) {
            if (booking.getCustomer().getId().equals(customerId)) {
                return false;
            }

        }
        restTemplate.delete("http://localhost:8081/customers/delete/{customerId}", customerId);
        return true;
    }

    public CustomerDTO getCustomerById(Long id) {
        return restTemplate.getForObject("http://localhost:8081/customers/{id}", CustomerDTO.class, id);
    }
    public CustomerDTO editById(CustomerDTO editedCustomer) {

        return restTemplate.exchange(
                "http://localhost:8081/customers/editCst",
                HttpMethod.PUT,
                new HttpEntity<>(editedCustomer),
                CustomerDTO.class
        ).getBody();
    }
}
