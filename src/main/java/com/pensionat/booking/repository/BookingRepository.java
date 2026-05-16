package com.pensionat.booking.repository;

import com.pensionat.booking.model.BookingEntity;
import com.pensionat.booking.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    boolean existsByCustomerIdAndBookingStatus(
            Long customerId,
            BookingStatus bookingStatus
    );

    boolean existsByRoomIdAndBookingStatusAndStartDateBeforeAndEndDateAfter(
            Long roomId,
            BookingStatus bookingStatus,
            LocalDate endDate,
            LocalDate startDate
    );
}