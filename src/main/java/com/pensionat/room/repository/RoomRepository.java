package com.pensionat.room.repository;

import com.pensionat.room.model.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository
        extends JpaRepository<RoomEntity, Integer> {
}