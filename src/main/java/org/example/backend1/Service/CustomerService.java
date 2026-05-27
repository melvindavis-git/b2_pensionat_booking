package org.example.backend1.Service;

import org.example.backend1.DTO.CustomerDTO;
import org.example.backend1.Model.Booking;
import org.example.backend1.Model.Customer;
import org.example.backend1.Repository.BookingRepository;
import org.example.backend1.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
                new RuntimeException("Kunden hittades ej."));
        CustomerDTO deletedCustomerDTO = CustomerToCustomerDTO(deletedCustomer);

        for (Booking booking : bookingRepo.findAll()) {
            if (booking.getCustomer().getId().equals(customerId)) {
                throw new RuntimeException(customerRepo.findById(customerId).get().getName() + " har en bokning.");
            }
        }
        customerRepo.deleteById(customerId);
        return deletedCustomerDTO;
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
