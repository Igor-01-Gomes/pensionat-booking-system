package com.pensionat.booking.repository;

import com.pensionat.booking.model.BookingEntity;
import com.pensionat.booking.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    boolean existsByCustomerIdAndBookingStatus(Long customerId, BookingStatus bookingStatus);

}