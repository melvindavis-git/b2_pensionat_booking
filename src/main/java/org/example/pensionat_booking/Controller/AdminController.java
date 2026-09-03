package org.example.pensionat_booking.Controller;

import org.example.pensionat_booking.DTO.CustomerDTO;
import org.example.pensionat_booking.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/resetdb")
    public ResponseEntity<List<CustomerDTO>> resetDatabaseData() {

        List<CustomerDTO> customers = adminService.resetDatabaseDevData();
        return ResponseEntity.ok().body(customers);
    }
}
