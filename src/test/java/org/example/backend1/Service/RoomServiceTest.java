package org.example.backend1.Service;

import org.example.backend1.BaseTest;
import org.example.backend1.DTO.RoomDTO;
import org.example.backend1.Model.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RoomServiceTest extends BaseTest {
    @Autowired
    private RoomService roomService;

    @Test
    void getAllRoomsTest() {
        List<RoomDTO> roomsDTO = roomService.getAllRooms();
        assertTrue(roomsDTO.size() == 4);
    }

    @Test
    void roomToRoomDTOTest() {
        Room room = new Room("Q1", true);
        RoomDTO roomDTO = roomService.RoomToRoomDTO(room);
        assertTrue(roomDTO.isDoubleRoom());
        assertTrue(roomDTO.getNr().equals("Q1"));
    }
}