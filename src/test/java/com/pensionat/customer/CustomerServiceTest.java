package com.pensionat.customer;

import com.pensionat.booking.model.BookingStatus;
import com.pensionat.booking.repository.BookingRepository;
import com.pensionat.customer.dto.CreateCustomerRequest;
import com.pensionat.customer.dto.UpdateCustomerRequest;
import com.pensionat.customer.model.CustomerEntity;
import com.pensionat.customer.repository.CustomerRepository;
import com.pensionat.customer.service.CustomerService;
import com.pensionat.exception.BadRequestException;
import com.pensionat.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private BookingRepository bookingRepository;
    @InjectMocks
    private CustomerService customerService;


    @Test
    void createCustomer_ShouldSaveAndReturnCustomer() {

        CreateCustomerRequest request = new CreateCustomerRequest(
                "Daniel",
                "Lyytikäinen",
                "Test@test.com",
                "Password123",
                "+46701234567");

        CustomerEntity savedCustomer = new CustomerEntity();
        savedCustomer.setFirstName("Daniel");
        savedCustomer.setLastName("Lyytikäinen");
        savedCustomer.setEmail("Test@test.com");
        savedCustomer.setHashedPassword("Password123");
        savedCustomer.setPhoneNumber("+46701234567");

        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(savedCustomer);

        CustomerEntity testResult = customerService.createCustomer(request);

        assertNotNull(testResult);
        assertEquals("Daniel", testResult.getFirstName());
        assertEquals("Lyytikäinen", testResult.getLastName());
        assertEquals("Test@test.com", testResult.getEmail());
        assertEquals("+46701234567", testResult.getPhoneNumber());
        assertNotEquals("Patric", testResult.getFirstName());

        verify(customerRepository).save(any(CustomerEntity.class));
    }

    @Test
    void givenValidRequest_WhenCreateCustomer_ThenRepositoryCalledOnce() {
        CreateCustomerRequest request = new CreateCustomerRequest(
                "Daniel",
                "Lyytikäinen",
                "Test@test.com",
                "Password123",
                "+46701234567");
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(new CustomerEntity());
        customerService.createCustomer(request);
        verify(customerRepository).save(any(CustomerEntity.class));
    }

    @Test
    void givenRepositoryFails_WhenCreateCustomer_ThenThrowException() {
        CreateCustomerRequest request = new CreateCustomerRequest(
                "Daniel",
                "Lyytikäinen",
                "Test@test.com",
                "Password123",
                "+46701234567");
        when(customerRepository.save(any(CustomerEntity.class))).thenThrow(new RuntimeException("Database error!"));

        assertThrows(RuntimeException.class, () -> customerService.createCustomer(request));
    }

    @Test
    void givenValidId_WhenDeleteCustomer_ThenCustomerIsDeleted() {
        Long customerId = 1L;
        when(customerRepository.existsById(customerId)).thenReturn(true);
        when(bookingRepository.existsByCustomerIdAndBookingStatus(customerId, BookingStatus.ACTIVE)).thenReturn(false);

        customerService.deleteCustomer(customerId);
        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    void givenInvalidId_WhenDeleteCustomer_ThenThrowNotFoundException() {
        Long customerId = 1L;
        when(customerRepository.existsById(customerId)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> customerService.deleteCustomer(customerId));
        verify(customerRepository, never()).deleteById(any());
    }

    @Test
    void givenCustomerWithActiveBooking_WhenDeleteCustomer_ThenThrowBadRequestException() {
        Long customerId = 1L;
        when(customerRepository.existsById(customerId)).thenReturn(true);
        when(bookingRepository.existsByCustomerIdAndBookingStatus(customerId, BookingStatus.ACTIVE)).thenReturn(true);

        assertThrows(BadRequestException.class, () -> customerService.deleteCustomer(customerId));
        verify(customerRepository, never()).deleteById(customerId);
    }

    @Test

    void givenValidRequest_WhenUpdateCustomer_ThenCustomerIsUpdated() {
        Long customerId = 1L;
        UpdateCustomerRequest request = new UpdateCustomerRequest(
                "Daniel",
                "Lyytikäinen",
                "UpdatedMail@mail.com",
                "NewPassword123",
                "+46701234567"

        );

        CustomerEntity existingCustomer = new CustomerEntity();

        existingCustomer.setFirstName("Igor");
        existingCustomer.setLastName("Gomes");
        existingCustomer.setEmail("OldMail@mail.com");
        existingCustomer.setHashedPassword("OldPassword123");
        existingCustomer.setPhoneNumber("+46707654321");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.isEmailAvailable(request.email(),customerId)).thenReturn(true);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(existingCustomer);

        CustomerEntity result = customerService.updateCustomer(customerId, request);

        assertNotNull(result);
        assertEquals("Daniel", result.getFirstName());
        assertEquals("UpdatedMail@mail.com", result.getEmail());
        verify(customerRepository, times(1)).save(any(CustomerEntity.class));
    }
    @Test
    void givenInvalidRequest_WhenUpdateCustomer_ThenThrowNotFoundException() {
        Long customerId = 1L;
        UpdateCustomerRequest request = new UpdateCustomerRequest(
                "Daniel",
                "Lyytikäinen",
                "UpdatedMail@mail.com",
                "NewPassword123",
                "+46701234567"

        );
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> customerService.updateCustomer(customerId, request));
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    void givenEmailAlreadyInUse_WhenUpdateCustomer_ThenThrowBadRequestException() {
        Long customerId = 1L;
        UpdateCustomerRequest request = new UpdateCustomerRequest(
                "Daniel",
                "Lyytikäinen",
                "UpdatedMail@mail.com",
                "NewPassword123",
                "+46701234567"

        );

        CustomerEntity existingCustomer = new CustomerEntity();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.isEmailAvailable(request.email(),customerId)).thenReturn(false);

        assertThrows(BadRequestException.class, () -> customerService.updateCustomer(customerId, request));
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }
}
