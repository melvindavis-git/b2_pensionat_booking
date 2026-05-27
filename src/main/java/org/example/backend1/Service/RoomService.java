package org.example.backend1.Service;

import org.example.backend1.DTO.RoomDTO;
import org.example.backend1.Model.Room;
import org.example.backend1.Repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository repo;

    public RoomService(RoomRepository repo) {
        this.repo = repo;
    }

    public List<RoomDTO> getAllRooms() {
        return repo.findAll().stream().map(r -> RoomToRoomDTO(r)).toList();
    }

    public RoomDTO RoomToRoomDTO(Room r) {
        return RoomDTO.builder().id(r.getId()).nr(r.getNr()).isDoubleRoom(r.isDoubleRoom()).build();
    }

}
