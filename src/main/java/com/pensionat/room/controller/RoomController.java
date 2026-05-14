package com.pensionat.room.controller;

import com.pensionat.room.model.RoomEntity;
import com.pensionat.room.service.RoomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/api/rooms")
    public List<RoomEntity> getAllRooms() {
        return roomService.getAllRooms();
    }
}