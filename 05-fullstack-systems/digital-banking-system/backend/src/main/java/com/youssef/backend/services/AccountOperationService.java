package com.youssef.backend.services;

import com.youssef.backend.dtos.AccountOperationDTO;
import com.youssef.backend.exeptions.BalanceNotSufficientException;
import com.youssef.backend.exeptions.BankAccountNotFoundException;

import java.util.List;

/**
 * Interface définissant les services liés aux opérations bancaires (débit, crédit, virement).
 */
public interface AccountOperationService {
    /**
     * Enregistre une opération bancaire.
     * @param accountOperationDTO Les données de l'opération
     * @return L'opération enregistrée
     */
    AccountOperationDTO saveAccountOperation(AccountOperationDTO accountOperationDTO);
    
    /**
     * Récupère la liste de toutes les opérations bancaires.
     * @return Liste des opérations
     */
    List<AccountOperationDTO> listAccountOperations();
    
    /**
     * Récupère les opérations d'un compte spécifique.
     * @param bankAccountId L'ID du compte bancaire
     * @return Liste des opérations du compte
     */
    List<AccountOperationDTO> getAccountOperationByAccountId(String bankAccountId);
    
    /**
     * Effectue un débit sur un compte.
     * @param bankAccountId L'ID du compte à débiter
     * @param amount Le montant à débiter
     * @param desc Description de l'opération
     * @return L'opération de débit créée
     * @throws BankAccountNotFoundException Si le compte n'existe pas
     * @throws BalanceNotSufficientException Si le solde est insuffisant
     */
    AccountOperationDTO debit(String bankAccountId, double amount, String desc) throws BankAccountNotFoundException, BalanceNotSufficientException;
    
    /**
     * Effectue un crédit sur un compte.
     * @param bankAccountId L'ID du compte à créditer
     * @param amount Le montant à créditer
     * @param desc Description de l'opération
     * @return L'opération de crédit créée
     * @throws BankAccountNotFoundException Si le compte n'existe pas
     */
    AccountOperationDTO credit(String bankAccountId, double amount, String desc) throws BankAccountNotFoundException;
    
    /**
     * Effectue un virement entre deux comptes.
     * @param accountIdSource L'ID du compte source
     * @param accountIdDestination L'ID du compte destination
     * @param amount Le montant du virement
     * @throws BankAccountNotFoundException Si l'un des comptes n'existe pas
     * @throws BalanceNotSufficientException Si le solde du compte source est insuffisant
     */
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
}
