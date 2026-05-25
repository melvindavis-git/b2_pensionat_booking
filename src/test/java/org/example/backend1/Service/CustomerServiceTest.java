package org.example.backend1.Service;

import org.example.backend1.Controller.BaseControllerTest;
import org.example.backend1.DTO.CustomerDTO;
import org.example.backend1.Model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest extends BaseControllerTest {

    @Autowired
    CustomerService customerService;
    @Test
    void getAllCustomers() {
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
      Customer customer = customerRepository.findAll().getFirst();
        customerService.deleteById(customer.getId());

    }
}