package org.example.backend1.Service;

import org.example.backend1.DTO.RegisterRequest;
import org.example.backend1.DTO.RegisterResponse;
import org.example.backend1.Model.Booking;
import org.example.backend1.Model.Customer;
import org.example.backend1.Repository.BookingRepository;
import org.example.backend1.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepo;
    private final BookingRepository bookingRepo;

    public CustomerService(CustomerRepository customerRepo, BookingRepository bookingRepo, BookingRepository bookingRepo1) {
        this.customerRepo = customerRepo;
        this.bookingRepo = bookingRepo;
    }

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }



    public RegisterResponse registerCustomer(RegisterRequest request) {
        List<Customer> customers = customerRepo.findAll();

        Customer newCustomer = new Customer(request.getName(), request.getEmail(), request.getPhone());

        customerRepo.save(newCustomer);

        return new RegisterResponse(newCustomer.getEmail()) ;
    }

    public List<Customer> deleteById(Long customerId) {

        boolean foundCustomer = false;
        for (Booking booking : bookingRepo.findAll()){
            if (booking.getCustomer().getId().equals(customerId)){
                foundCustomer=true;
            }
        }
        if (!foundCustomer) {
            customerRepo.deleteById(customerId);
        }
        return customerRepo.findAll();
    }

}
