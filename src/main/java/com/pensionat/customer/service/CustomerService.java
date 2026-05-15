package com.pensionat.customer.service;


import com.pensionat.booking.repository.BookingRepository;
import com.pensionat.customer.dto.CreateCustomerRequest;
import com.pensionat.customer.model.CustomerEntity;
import com.pensionat.customer.repository.CustomerRepository;
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
        return customerRepository.save(customer);
    }
}
