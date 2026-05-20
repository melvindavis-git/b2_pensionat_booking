package org.example.backend1.Controller;

import org.example.backend1.DTO.RegisterRequest;
import org.example.backend1.DTO.RegisterResponse;
import org.example.backend1.Model.Customer;
import org.example.backend1.Service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerService service;
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("customers")
    public List<Customer> getAllCustomers() {
        log.info("GET request for all customers");
        List<Customer> customers = service.getAllCustomers();
        log.info("Returned {} customers", customers.size());
        return service.getAllCustomers();
    }

    @PostMapping("customers/register")
    public RegisterResponse registerCustomers(@RequestBody RegisterRequest request) {
        log.info("POST request to register customer");
        log.info("Customer {} registered successfully", request.getName());
        return service.registerCustomer(request);
    }

    @DeleteMapping("customers/delete/{id}")
    public List<Customer> deleteById(@PathVariable Long id) {
        log.info("DELETE request to delete customer");
        List<Customer> customers = service.deleteById(id);
        log.info("Customer with id {} deleted successfully", id);
        return customers;
    }

}
