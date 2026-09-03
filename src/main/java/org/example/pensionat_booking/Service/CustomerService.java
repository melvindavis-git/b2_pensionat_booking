package org.example.pensionat_booking.Service;

import org.example.pensionat_booking.DTO.CustomerDTO;
import org.example.pensionat_booking.Model.Booking;
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

    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        try {
            List<CustomerDTO> customers = restTemplate.getForObject(baseUrl + "/customers/all", List.class);
            return ResponseEntity.status(HttpStatus.OK).body(customers);

        } catch (HttpClientErrorException.BadRequest e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    public ResponseEntity<CustomerDTO> registerCustomer(CustomerDTO inputCustomer) {

        CustomerDTO savedCst = new CustomerDTO();
        try{
            savedCst = restTemplate.postForObject(baseUrl + "/customers/register", inputCustomer, CustomerDTO.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCst);
        }
        catch (HttpClientErrorException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(savedCst);
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
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    public ResponseEntity<CustomerDTO> getCustomerById(Long id) {

        CustomerDTO customerByID = new CustomerDTO();
        try {
            restTemplate.getForEntity(baseUrl + "/customers/{id}", CustomerDTO.class, id);
        }
        catch (HttpClientErrorException.NotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customerByID);
        }
        catch (HttpClientErrorException.BadRequest e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customerByID);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        customerByID = restTemplate.getForObject(baseUrl + "/customers/{id}", CustomerDTO.class, id);
        return ResponseEntity.status(HttpStatus.OK).body(customerByID);
    }
    public ResponseEntity<CustomerDTO> editById(CustomerDTO editedCustomer) {

        try {
            CustomerDTO editedCst = restTemplate.exchange(baseUrl + "/customers/editCst",
                    HttpMethod.PUT,
                    new HttpEntity<>(editedCustomer),
                    CustomerDTO.class
            ).getBody();
            return ResponseEntity.status(HttpStatus.OK).body(editedCst);

        } catch (HttpClientErrorException.NotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (HttpClientErrorException.BadRequest e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
