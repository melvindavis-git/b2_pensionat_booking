package org.example.backend1.Controller;

import jakarta.validation.Valid;
import org.example.backend1.DTO.CustomerDTO;
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
    public List<CustomerDTO> getAllCustomers() {
        log.info("GET request for all customers");
        List<CustomerDTO> customers = service.getAllCustomers();
        log.info("Returned {} customers", customers.size());
        return service.getAllCustomers();
    }

    @PostMapping("customers/register")
    public CustomerDTO registerCustomers(@Valid @RequestBody CustomerDTO customerDTO) {
        log.info("POST request to register customer");
        log.info("Customer {} registered successfully", customerDTO.getName());
        return service.registerCustomer(customerDTO);
    }

    @DeleteMapping("customers/delete/{id}")
    public List<CustomerDTO> deleteById(@PathVariable Long id) {
        log.info("DELETE request to delete customer");
        List<CustomerDTO> customersDTO = service.deleteById(id);
        log.info("Customer with id {} deleted successfully", id);
        return customersDTO;
    }

}
