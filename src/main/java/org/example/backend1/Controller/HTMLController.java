package org.example.backend1.Controller;


import org.example.backend1.DTO.BookingDTO;
import org.example.backend1.DTO.CustomerDTO;
import org.example.backend1.Service.BookingService;
import org.example.backend1.Service.CustomerService;
import org.example.backend1.Service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HTMLController {

    private final CustomerService customerService;
    private final RoomService roomService;
    private final BookingService bookingService;

    public HTMLController(CustomerService customerService, RoomService roomService, BookingService bookingService) {
        this.customerService = customerService;
        this.roomService = roomService;
        this.bookingService = bookingService;
    }

    @GetMapping("/")
    public String homePage(){
        return "index";
    }

    @GetMapping("/customers")
    public String customersPage(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customers";
    }

    @GetMapping("/rooms")
    public String roomsPage(Model model){
        model.addAttribute("rooms", roomService.getAllRooms());
        return "rooms";
    }

    @GetMapping("/bookings")
    public String bookingsPage(Model model){
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "bookings";
    }

    @PostMapping("/customers/delete/{id}")
    public String deleteCustomerById(@PathVariable Long id, Model model) {
        try {
            customerService.deleteById(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customers";
    }

    @PostMapping("/bookings/delete/{id}")
    public String removeBooking(@PathVariable Long id, Model model) {
        try {
            bookingService.removeBooking(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "bookings";
    }

    @GetMapping("/bookings/new")
    public String newBookingPage(){
        return "createBooking";
    }

    @PostMapping("/bookings")
    public String bookRoom(@RequestParam String startDate, @RequestParam String endDate,
                               @RequestParam boolean isDoubleRoom, @RequestParam Long customerId) {
        bookingService.createBooking(startDate, endDate, isDoubleRoom, customerId);
        return "bookings";
    }



}
