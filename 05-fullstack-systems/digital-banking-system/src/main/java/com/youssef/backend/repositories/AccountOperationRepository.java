package com.youssef.backend.repositories;

import com.youssef.backend.entities.AccountOperation;
import com.youssef.backend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository JPA pour l'entité AccountOperation.
 * Fournit les méthodes CRUD standard et des méthodes pour rechercher des opérations.
 */
@Repository
public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
    /**
     * Trouve toutes les opérations bancaires associées à un compte spécifique.
     * @param bankAccountId L'ID du compte bancaire
     * @return Liste des opérations du compte
     */
    List<AccountOperation> findAccountOperationByBankAccountId(String  bankAccountId);

}
