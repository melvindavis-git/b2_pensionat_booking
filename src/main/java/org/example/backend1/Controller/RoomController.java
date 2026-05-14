package org.example.backend1.Controller;

import org.example.backend1.Model.Room;
import org.example.backend1.Repository.RoomRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoomController {

    private final RoomRepository repo;

    public RoomController(RoomRepository repo) {
        this.repo = repo;
    }

    @GetMapping("rooms")
    public List<Room> getAllRooms() {
        return repo.findAll();
    }

    @GetMapping("rooms/{nr}/{cId}")
    public List<Room> bookRoom(@PathVariable String nr, @PathVariable Long cId) {
        Room currentRoom = repo.findRoomByNr(nr);
        currentRoom.setCustomerId(cId);
        currentRoom.setOccupied(true);
        return repo.findAll();
    }

}
