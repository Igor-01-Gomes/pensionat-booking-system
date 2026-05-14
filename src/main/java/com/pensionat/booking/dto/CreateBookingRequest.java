package com.pensionat.booking.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateBookingRequest (

    @NotNull(message = "Valid customer-id must be entered")
    Long  customerId,

    @NotNull(message = "Valid room-id must be entered")
    Long roomId,

    @NotNull(message = "Check-in date muste be entered")
    @FutureOrPresent(message = "Check-in date can not be earlier than today.")
    LocalDate startDate,

    @NotNull(message = "Check-out date must be entered.")
    LocalDate endDate
){
}
