package org.example.backend1.Controller;

import org.example.backend1.Model.Customer;
import org.example.backend1.Repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerRepository repo;

    public CustomerController(CustomerRepository repo) {
        this.repo = repo;
    }

    @GetMapping("customers")
    public List<Customer> getAllCustomers() {
        return repo.findAll();
    }

    @PostMapping("customers/register")
    public List<Customer> registerCustomers(@RequestBody Customer c) {
        repo.save(c);
        return repo.findAll();
    }

    @GetMapping("customers/delete/{id}")
    public List<Customer> deleteById(@PathVariable long id) {
        repo.deleteById(id);
        return repo.findAll();
    }

}
