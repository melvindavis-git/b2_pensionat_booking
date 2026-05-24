package org.example.backend1.Controller;


import org.example.backend1.Service.BookingService;
import org.example.backend1.Service.CustomerService;
import org.example.backend1.Service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

//    @RequestMapping("/formGreeting")
//    public String formGreeting(){
//        return "formGreetingStart.html";
//    }
//
//    @RequestMapping("/testing")
//    public String testing(@RequestParam String name, Model model){
//        model.addAttribute("name", name);
//        return "index";
//    }


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



}
