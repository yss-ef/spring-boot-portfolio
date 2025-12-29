package com.youssef.backend.repositories;

import com.youssef.backend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository JPA pour l'entité BankAccount.
 * Fournit les méthodes CRUD standard et des méthodes personnalisées pour rechercher des comptes.
 */
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    /**
     * Trouve les comptes bancaires associés à un ID client spécifique.
     * @param customerId L'ID du client
     * @return Liste des comptes bancaires du client
     */
    List<BankAccount> findByCustomerId(Long customerId);
    
    /**
     * Trouve les comptes bancaires associés à un ID client spécifique (alias de findByCustomerId).
     * @param customerId L'ID du client
     * @return Liste des comptes bancaires du client
     */
    List<BankAccount> findBankAccountsByCustomerId(Long customerId);
}
