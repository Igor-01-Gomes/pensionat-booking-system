package com.pensionat.config;

import com.pensionat.room.model.RoomEntity;
import com.pensionat.room.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedRooms(RoomRepository roomRepository) {
        return args -> {
            long roomCount = roomRepository.count();

            if (roomCount > 0) {
                System.out.println("Database already contains " + roomCount + " - skipping seed.");
                return;
            }

            System.out.println("Database Empty - Seeding Rooms: ");

            roomRepository.save(buildRoom(101, 1, 800));
            roomRepository.save(buildRoom(102, 1, 800));
            roomRepository.save(buildRoom(103, 1, 800));
            roomRepository.save(buildRoom(104, 1, 800));
            roomRepository.save(buildRoom(105, 1, 900));

            roomRepository.save(buildRoom(201, 2, 1200));
            roomRepository.save(buildRoom(202, 2, 1200));
            roomRepository.save(buildRoom(203, 2, 1200));
            roomRepository.save(buildRoom(204, 2, 1500));
            roomRepository.save(buildRoom(205, 2, 2000));

            System.out.println("Done - 10 rooms seeded.");
        };
    }

    private RoomEntity buildRoom(int roomNumber, int beds, int pricePerNight) {
        RoomEntity room = new RoomEntity();
        room.setRoomNumber(roomNumber);
        room.setBeds(beds);
        room.setPricePerNight(pricePerNight);
        return room;
    }
}