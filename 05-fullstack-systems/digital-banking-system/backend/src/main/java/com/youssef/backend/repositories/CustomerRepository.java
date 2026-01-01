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

    /**
     * Trouve un client par son identifiant Telegram.
     * Utilisé pour l'authentification via le bot.
     * @param telegramId L'ID Telegram du client.
     * @return Le client correspondant ou null.
     */
    Customer findByTelegramId(Long telegramId);

    /**
     * Trouve un client par son adresse email.
     * Utilisé pour lier un compte Telegram à un client existant.
     * @param email L'email du client.
     * @return Le client correspondant ou null.
     */
    Customer findByEmail(String email);
}
