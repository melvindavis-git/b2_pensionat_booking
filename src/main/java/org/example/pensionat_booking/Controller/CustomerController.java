package org.example.pensionat_booking.Controller;

import jakarta.validation.Valid;
import org.example.pensionat_booking.DTO.CustomerDTO;
import org.example.pensionat_booking.Service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping()
    public List<CustomerDTO> getAllCustomers() {
        log.info("GET request for all customers");
        return service.getAllCustomers();
    }



    @PostMapping("/register")
    public ResponseEntity<CustomerDTO> registerCustomers(@Valid @RequestBody CustomerDTO customerDTO) {
        log.info("POST request to register customer");
        log.info("Customer {} registered successfully", customerDTO.getName());
        return service.registerCustomer(customerDTO);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteById(@PathVariable Long id) {
        log.info("DELETE request to delete customer");
        boolean result = service.deleteById(id);
        log.info("Customer with id {} deleted successfully", id);
        return result;
    }

    @PutMapping("/edit")
    public CustomerDTO editCustomer(@RequestBody CustomerDTO customerDTO) {
        return service.editById(customerDTO);
    }



}
