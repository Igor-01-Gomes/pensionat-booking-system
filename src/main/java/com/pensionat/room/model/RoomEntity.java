package com.pensionat.room.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "room")
public class RoomEntity {

    public RoomEntity() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private int roomNumber;

    @Min(1)
    @Column(nullable = false)
    private int beds;

    @Min(0)
    @Column(nullable = false)
    private int pricePerNight;
}