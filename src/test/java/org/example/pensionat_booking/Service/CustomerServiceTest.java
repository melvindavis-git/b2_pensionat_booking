package org.example.pensionat_booking.Service;

import org.example.pensionat_booking.BaseTest;
import org.example.pensionat_booking.DTO.CustomerDTO;
import org.example.pensionat_booking.Model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest extends BaseTest {

    @Autowired
    CustomerService customerService;

//    @Test
//    void getAllCustomersTest() {
//        List<CustomerDTO> customersDTO = customerService.getAllCustomers();
//        assertNotNull(customersDTO);
//        assertTrue(customersDTO.size() == 4);
//        assertFalse(customersDTO.contains(customersDTO.get(0).getName().equals("Mikael")));
//    }

    @Test
    void customerToCustomerDTOTest() {
        Customer customer = new Customer("Name", "email@emial.se", "0709112233");
        CustomerDTO customerDTO = customerService.CustomerToCustomerDTO(customer);
        assertNotNull(customerDTO);
        assertTrue(customerDTO.getName().equals("Name"));
    }

//    @Test
//    void registerCustomerTest() {
//        CustomerDTO customerDTO = new CustomerDTO(null, "Testsson", "testsson@test.se", "0709112233");
//        CustomerDTO customer = customerService.registerCustomer(customerDTO);
//
//        assertNotNull(customer);
//        assertTrue(customer.getName().equals("Testsson"));
//        assertTrue(customer.getEmail().equals("testsson@test.se"));
//        assertTrue(customer.getPhone().equals("0709112233"));
//        assertFalse(customer.getName().equals("OtherName"));
//    }

    @Test
    void deleteByIdTest() {
        List<Customer> customers = customerRepository.findAll();
        customerService.deleteById(customers.getLast().getId());

        assertFalse(customerRepository.findById(customers.getLast().getId()).isPresent());
        assertThrows(RuntimeException.class, () -> customerService.deleteById(customers.getLast().getId()));
    }

    @Test
    void getCustomerByIdTest() {
        Customer customer = customerRepository.findAll().getFirst();
        CustomerDTO customerAsDto = customerService.getCustomerById(customer.getId());

        assertNotNull(customerAsDto);
        assertEquals(customer.getName(), customerAsDto.getName());
    }

    @Test
    void editById() {
        Customer customer = customerRepository.findAll().getFirst();
        CustomerDTO customerDto = customerService.editById(customer.getId(), "NewName", "new@email.com", "0701998877");
        assertEquals(customerDto.getName(), "NewName");
        assertEquals(customerDto.getEmail(), "new@email.com");
        assertEquals(customerDto.getPhone(), "0701998877");
        assertThrows(RuntimeException.class, () -> customerService.editById(customer.getId(), "name", "new@mail", "0701998877"));


    }
}