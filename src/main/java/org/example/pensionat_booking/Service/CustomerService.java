package org.example.pensionat_booking.Service;

import org.example.pensionat_booking.DTO.CustomerDTO;
import org.example.pensionat_booking.Model.Booking;
import org.example.pensionat_booking.Model.Customer;
import org.example.pensionat_booking.Repository.BookingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CustomerService {

    private final BookingRepository bookingRepo;
    RestTemplate restTemplate = new RestTemplate();
    String baseUrl;

    public CustomerService(BookingRepository bookingRepo, @Value("${customer-service.base-url}") String baseUrl) {
        this.bookingRepo = bookingRepo;
        this.baseUrl = baseUrl;
    }

    public List<CustomerDTO> getAllCustomers() {
        return restTemplate.getForObject(baseUrl + "/customers/all", List.class);
    }

    public CustomerDTO CustomerToCustomerDTO(Customer c) {
        return CustomerDTO.builder().id(c.getId()).name(c.getName()).email(c.getEmail()).phone(c.getPhone()).build();
    }


    public ResponseEntity<CustomerDTO> registerCustomer(CustomerDTO inputCustomer) {

        try{
            CustomerDTO savedCst = restTemplate.postForObject(baseUrl + "/customers/register", inputCustomer, CustomerDTO.class);
            return ResponseEntity.ok(savedCst);
        }
        catch (Exception e){
            System.out.println(e);
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Void> deleteById(Long customerId) {

        for (Booking booking : bookingRepo.findAll()) {
            if (booking.getCustomerId().equals(customerId)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Personen har bokningar");
            }

        }
        try {
            restTemplate.delete(baseUrl + "/customers/delete/{customerId}", customerId);
            return ResponseEntity.ok().build();
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    public CustomerDTO getCustomerById(Long id) {
        return restTemplate.getForObject(baseUrl + "/customers/{id}", CustomerDTO.class, id);
    }
    public CustomerDTO editById(CustomerDTO editedCustomer) {

        return restTemplate.exchange(
                baseUrl + "/customers/editCst",
                HttpMethod.PUT,
                new HttpEntity<>(editedCustomer),
                CustomerDTO.class
        ).getBody();
    }
}
