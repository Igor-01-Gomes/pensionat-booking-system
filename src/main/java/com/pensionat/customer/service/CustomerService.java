package com.pensionat.customer.service;


import com.pensionat.booking.model.BookingStatus;
import com.pensionat.booking.repository.BookingRepository;
import com.pensionat.customer.dto.CreateCustomerRequest;
import com.pensionat.customer.dto.UpdateCustomerRequest;
import com.pensionat.customer.model.CustomerEntity;
import com.pensionat.customer.repository.CustomerRepository;
import com.pensionat.exception.BadRequestException;
import com.pensionat.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;


    public CustomerService(CustomerRepository customerRepository, BookingRepository bookingRepository) {
        this.customerRepository = customerRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }

    public CustomerEntity createCustomer(CreateCustomerRequest request) {
        CustomerEntity customer = new CustomerEntity();
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setEmail(request.email());
        customer.setHashedPassword(request.hashedPassword());
        customer.setPhoneNumber(request.phone());
        customerRepository.save(customer);
        return customer;
    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new NotFoundException("Customer not found");
        }
        if (bookingRepository.existsByCustomerIdAndBookingStatus(id, BookingStatus.ACTIVE)) {
            throw new BadRequestException("Customer cannot be deleted with an active booking");
        }
        customerRepository.deleteById(id);
    }

    public CustomerEntity updateCustomer(Long id, UpdateCustomerRequest request) {
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        if(customerRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new BadRequestException("Email is already in use");
        }
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setEmail(request.email());
        customer.setHashedPassword(request.hashedPassword());
        customer.setPhoneNumber(request.phone());
        customerRepository.save(customer);
        return customer;
    }
}
