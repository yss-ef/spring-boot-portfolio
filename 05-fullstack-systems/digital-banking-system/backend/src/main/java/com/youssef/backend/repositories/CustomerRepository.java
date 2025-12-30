package com.youssef.backend.repositories;

import com.youssef.backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository JPA pour l'entité Customer.
 * Fournit les méthodes CRUD standard pour gérer les clients.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByNameContainsIgnoreCase(String keyword);
}
