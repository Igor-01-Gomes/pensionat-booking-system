package com.pensionat.booking.service;

import com.pensionat.booking.model.BookingEntity;
import com.pensionat.booking.model.BookingStatus;
import com.pensionat.booking.dto.CreateBookingRequest;
import com.pensionat.booking.repository.BookingRepository;
import com.pensionat.customer.model.CustomerEntity;
import com.pensionat.customer.repository.CustomerRepository;
import com.pensionat.exception.BadRequestException;
import com.pensionat.exception.NotFoundException;
import com.pensionat.room.model.RoomEntity;
import com.pensionat.room.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;

    public BookingService(BookingRepository bookingRepository, CustomerRepository customerRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.roomRepository = roomRepository;
    }

    public List<BookingEntity> getAllBookings() {
        return bookingRepository.findAll();
    }

    public BookingEntity createBooking(CreateBookingRequest request) {
        CustomerEntity customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        RoomEntity room = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new NotFoundException("Room not found"));

        if (!request.endDate().isAfter(request.startDate())) {
            throw new BadRequestException("Check-out date must be after check-in date");
        }

        boolean roomAlreadyBooked =
                bookingRepository.existsByRoomIdAndBookingStatusAndStartDateBeforeAndEndDateAfter(
                        room.getId(),
                        BookingStatus.ACTIVE,
                        request.endDate(),
                        request.startDate()
                );
        if (roomAlreadyBooked) {
            throw new BadRequestException("Room is already booked on selected dates");
        }

        BookingEntity booking = new BookingEntity(
                customer,
                room,
                request.startDate(),
                request.endDate(),
                BookingStatus.ACTIVE
        );

        return  bookingRepository.save(booking);
    }


}
