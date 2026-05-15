package org.example.backend1.Service;

import org.example.backend1.Model.Customer;
import org.example.backend1.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public List<Customer> getAllCustomers() {
        return repo.findAll();
    }

    public List<Customer> registerCustomer(Customer c) {
        repo.save(c);
        return repo.findAll();
    }

    public List<Customer> deleteById(Long id) {
        repo.deleteById(id);
        return repo.findAll();
    }

}
