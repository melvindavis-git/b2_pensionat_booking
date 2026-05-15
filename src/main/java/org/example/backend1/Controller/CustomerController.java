package org.example.backend1.Controller;

import org.example.backend1.Model.Customer;
import org.example.backend1.Service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("customers")
    public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }

    @PostMapping("customers/register")
    public List<Customer> registerCustomers(@RequestBody Customer c) {
        return service.registerCustomer(c);
    }

    @GetMapping("customers/delete/{id}")
    public List<Customer> deleteById(@PathVariable Long id) {
        return service.deleteById(id);
    }

}
