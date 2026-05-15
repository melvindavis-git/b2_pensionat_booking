package org.example.backend1.Controller;

import org.example.backend1.Model.Room;
import org.example.backend1.Service.RoomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoomController {

    private final RoomService service;

    public RoomController(RoomService service) {
        this.service = service;
    }

    @GetMapping("rooms")
    public List<Room> getAllRooms() {
        return service.getAllRooms();
    }

    @GetMapping("rooms/{nr}/{cId}")
    public List<Room> bookRoom(@PathVariable String nr, @PathVariable Long cId) {
        return service.bookRoom(nr, cId);
    }

}
