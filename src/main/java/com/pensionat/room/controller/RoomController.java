package com.pensionat.room.controller;

import com.pensionat.room.model.RoomEntity;
import com.pensionat.room.service.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<RoomEntity> getAllRooms() {
        return roomService.getAllRooms();
    }

    @PostMapping
    public RoomEntity createRoom(@RequestBody RoomEntity room) {
        return roomService.createRoom(room);
    }
}