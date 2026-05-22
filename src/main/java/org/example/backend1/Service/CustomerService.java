package org.example.backend1.Service;

import org.example.backend1.DTO.CustomerDTO;
import org.example.backend1.Model.Booking;
import org.example.backend1.Model.Customer;
import org.example.backend1.Repository.BookingRepository;
import org.example.backend1.Repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepo;
    private final BookingRepository bookingRepo;

    public CustomerService(CustomerRepository customerRepo, BookingRepository bookingRepo, BookingRepository bookingRepo1) {
        this.customerRepo = customerRepo;
        this.bookingRepo = bookingRepo;
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepo.findAll().stream().map(c -> CustomerToCustomerDTO(c)).toList();
    }

    public CustomerDTO CustomerToCustomerDTO(Customer c) {
        return CustomerDTO.builder().id(c.getId()).name(c.getName()).email(c.getEmail()).phone(c.getPhone()).build();
    }


    public CustomerDTO registerCustomer(CustomerDTO customerDTO) {

        Customer newCustomer = new Customer(customerDTO.getName(), customerDTO.getEmail(), customerDTO.getPhone());

        customerRepo.save(newCustomer);

        return CustomerToCustomerDTO(newCustomer);
    }

    public CustomerDTO deleteById(Long customerId) {

        Customer deletedCustomer = customerRepo.findById(customerId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kunden hittades ej."));
        CustomerDTO deletedCustomerDTO = CustomerToCustomerDTO(deletedCustomer);

        for (Booking booking : bookingRepo.findAll()) {
            if (booking.getCustomer().getId().equals(customerId)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kunden har en bokning.");
            }
        }
        customerRepo.deleteById(customerId);
        return deletedCustomerDTO;
    }

}
