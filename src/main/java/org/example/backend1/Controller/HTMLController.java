package org.example.backend1.Controller;


import jakarta.validation.ConstraintViolationException;
import org.example.backend1.DTO.BookingDTO;
import org.example.backend1.DTO.RoomDTO;
import org.example.backend1.Model.Customer;
import org.example.backend1.Service.BookingService;
import org.example.backend1.Service.CustomerService;
import org.example.backend1.Service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String homePage() {
        return "index";
    }

    @GetMapping("/customers")
    public String customersPage(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customers";
    }

    @GetMapping("/rooms")
    public String roomsPage(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        return "rooms";
    }

    @GetMapping("/bookings")
    public String bookingsPage(Model model) {
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

    @GetMapping("/customers/edit/{id}")
    public String editCustomerPage(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        return "editCustomer";
    }


    @PostMapping("/customers/edit/{id}")
    public String editCustomerById(@PathVariable Long id, @RequestParam String name,
                                   @RequestParam String email, @RequestParam String phone, Model model) {
        customerService.editById(id, name, email, phone);
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


    @GetMapping("/bookings/newBooking")
    public String createBookingPage(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "createBooking";
    }

    @PostMapping("/bookings")
    public String bookRoom(Model model, @RequestParam String startDate, @RequestParam String endDate,
                           @RequestParam boolean isDoubleRoom, @RequestParam int extraBeds, @RequestParam Long customerId) {
        try {
            BookingDTO currentBooking = bookingService.createBooking(startDate, endDate, isDoubleRoom, customerId, extraBeds);
            model.addAttribute("bookings", bookingService.getAllBookings());
            model.addAttribute("doubleRoom", bookingService.canAddBeds(currentBooking.getId(), extraBeds));
            currentBooking.setExtraBeds(extraBeds);

            return "bookings";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("customers", customerService.getAllCustomers());
            return "createBooking";
        }

    }

    @GetMapping("bookings/edit/{id}")
    public String editBookingPage(@PathVariable Long id, Model model) {
        model.addAttribute("booking", bookingService.getBookingById(id));
        return "editBooking";
    }

    @PostMapping("bookings/edit/{id}")
    public String editBookingById(@PathVariable Long id, @RequestParam String startDate,
                                  @RequestParam String endDate, Model model) {
        try {
            bookingService.editById(id, startDate, endDate);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("booking", bookingService.getBookingById(id));
            return "editBooking";
        }
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "bookings";
    }


    @GetMapping("/customers/register")
    public String registerCustomerPage() {
        return "registerCustomer";
    }

    @PostMapping("/customers/register")
    public String formGreetingReceiver(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            Model model) {

        try {
            Customer newCustomer = new Customer(name, email, phone);
            customerService.registerCustomer(customerService.CustomerToCustomerDTO(newCustomer));
            model.addAttribute("customername", name);
            model.addAttribute("customers", customerService.getAllCustomers());
            return "customers";

        } catch (ConstraintViolationException e) {
            List<String> errors = e.getConstraintViolations()
                    .stream()
                    .map(v -> v.getMessage())
                    .toList();

            StringBuilder error = new StringBuilder();
            for (String s : errors) {
                error.append(s).append(" ");
            }

            model.addAttribute("error", error.toString());
            model.addAttribute("name", name);
            model.addAttribute("email", email);
            model.addAttribute("phone", phone);
            return "registerCustomer";
        }
    }


    @GetMapping("/rooms/available-rooms")
    public String availableRoomsPage(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        return "listAvailableRooms";
    }

    @PostMapping("rooms/available-rooms")
    public String availableRoomsSearch(@RequestParam String startDate, String endDate, boolean isDoubleRoom, Model model) {
        try {
            model.addAttribute("rooms", roomService.getAllRooms());
            List<RoomDTO> availableRooms = bookingService.canBook(startDate, endDate, isDoubleRoom);
            model.addAttribute("availableRooms", availableRooms);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "listAvailableRooms";
    }

}
