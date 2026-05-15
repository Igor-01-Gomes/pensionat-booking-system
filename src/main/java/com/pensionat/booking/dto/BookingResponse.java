package com.pensionat.booking.dto;

import com.pensionat.booking.model.BookingEntity;
import com.pensionat.booking.model.BookingStatus;

import java.time.LocalDate;

public record BookingResponse(
        Long id,
        Long customerId,
        Long roomId,
        LocalDate startDate,
        LocalDate endDate,
        BookingStatus status
) {
    public static BookingResponse from(BookingEntity entity) {
        return new BookingResponse(
                entity.getId(),
                entity.getCustomer().getId(),
                entity.getRoom().getId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getBookingStatus()
        );
    }
}