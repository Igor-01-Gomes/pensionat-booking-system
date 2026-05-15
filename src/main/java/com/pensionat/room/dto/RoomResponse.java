package com.pensionat.room.dto;

import com.pensionat.room.model.RoomEntity;

public record RoomResponse(
        Long id,
        int roomNumber,
        int beds,
        int pricePerNight
)
{
    public static RoomResponse from(RoomEntity entity) {
        return new RoomResponse(
                entity.getId(),
                entity.getRoomNumber(),
                entity.getBeds(),
                entity.getPricePerNight()
        );
    }
}