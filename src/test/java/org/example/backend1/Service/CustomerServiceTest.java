package org.example.backend1.Service;

import org.example.backend1.BaseTest;
import org.example.backend1.DTO.CustomerDTO;
import org.example.backend1.Model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest extends BaseTest {

    @Autowired
    CustomerService customerService;

    @Test
    void getAllCustomers() {
        List<CustomerDTO> customerDTO = customerService.getAllCustomers();
        assertNotNull(customerDTO);
        assertTrue(customerDTO.size() == 4);
        assertFalse(customerDTO.contains(customerDTO.get(0).getName().equals("Mikael")));
    }

    @Test
    void registerCustomerTest() {
        CustomerDTO customerDTO = new CustomerDTO(null, "Testsson", "testsson@test.se", "0709112233");
        CustomerDTO customer = customerService.registerCustomer(customerDTO);

        assertNotNull(customer);
        assertTrue(customer.getName().equals("Testsson"));
        assertTrue(customer.getEmail().equals("testsson@test.se"));
        assertTrue(customer.getPhone().equals("0709112233"));
        assertFalse(customer.getName().equals("OtherName"));
    }

    @Test
    void deleteByIdTest() {
        List<Customer> customers = customerRepository.findAll();
        customerService.deleteById(customers.getLast().getId());

        assertFalse(customerRepository.findById(customers.getLast().getId()).isPresent());

        customerService.deleteById(customers.getFirst().getId());
    }

    @Test
    void customerToCustomerDTO() {
        Customer customer = new Customer("Name", "email@emial.se", "0709112233");
        CustomerDTO customerDTO = customerService.CustomerToCustomerDTO(customer);
        assertNotNull(customerDTO);
        assertTrue(customerDTO.getName().equals("Name"));
    }
}