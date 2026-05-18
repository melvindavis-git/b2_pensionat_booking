package org.example.backend1.Service;

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

    public List<Room> getAllRooms(){
        return repo.findAll();
    }

    public List<Room> bookRoom(String nr, Long cId){
        Room currentRoom = repo.findRoomByNr(nr);
        return repo.findAll();
    }

}
