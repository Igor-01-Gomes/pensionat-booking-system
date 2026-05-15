package com.pensionat.customer;

import com.pensionat.customer.dto.CreateCustomerRequest;
import com.pensionat.customer.model.CustomerEntity;
import com.pensionat.customer.repository.CustomerRepository;
import com.pensionat.customer.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
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
}
