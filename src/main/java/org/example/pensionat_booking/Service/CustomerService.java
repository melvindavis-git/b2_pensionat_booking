package org.example.pensionat_booking.Service;

import org.example.pensionat_booking.DTO.CustomerDTO;
import org.example.pensionat_booking.Model.Booking;
import org.example.pensionat_booking.Model.Customer;
import org.example.pensionat_booking.Repository.BookingRepository;
import org.example.pensionat_booking.Repository.CustomerRepository;
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
        return CustomerToCustomerDTO(Objects.requireNonNull(customerRepo.findById(id).orElse(null)));
    }


    public CustomerDTO editById(Long customerId, String name, String email, String phone) {
        Customer editedCustomer = customerRepo.findById(customerId).orElseThrow(() -> new RuntimeException("Kunden hittades ej"));
        editedCustomer.setName(name);
        editedCustomer.setEmail(email);
        editedCustomer.setPhone(phone);
        customerRepo.save(editedCustomer);
        return CustomerToCustomerDTO(editedCustomer);
    }

}
