package com.pensionat.customer.repository;

import com.pensionat.customer.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository
        extends JpaRepository<CustomerEntity, Long> {
    boolean existsByEmailAndIdNot(String email, Long id);
}