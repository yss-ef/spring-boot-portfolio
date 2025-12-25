package com.youssef.backend.services;

import com.youssef.backend.dtos.BankAccountDTO;
import com.youssef.backend.dtos.CurrentAccountDTO;
import com.youssef.backend.dtos.CustomerDTO;
import com.youssef.backend.dtos.SavingAccountDTO;
import com.youssef.backend.exeptions.BankAccountNotFoundException;

import java.util.List;

/**
 * Interface définissant les services liés à la gestion des comptes bancaires.
 */
public interface BankAccountService {
    /**
     * Enregistre un compte courant.
     * @param currentAccountDTO Les données du compte courant
     * @return Le compte courant enregistré
     */
    CurrentAccountDTO saveCurrentAccount(CurrentAccountDTO currentAccountDTO);
    
    /**
     * Enregistre un compte épargne.
     * @param savingAccountDTO Les données du compte épargne
     * @return Le compte épargne enregistré
     */
    SavingAccountDTO saveSavingAccount(SavingAccountDTO savingAccountDTO);
    
    /**
     * Récupère la liste de tous les comptes bancaires.
     * @return Liste des comptes bancaires
     */
    List<BankAccountDTO> listBankAccounts();
    
    /**
     * Récupère la liste des comptes bancaires d'un client spécifique.
     * @param customerId L'ID du client
     * @return Liste des comptes du client
     */
    List<BankAccountDTO> listBankAccountsByCustomerId(Long customerId);
    
    /**
     * Trouve un compte bancaire par son ID.
     * @param id L'ID du compte
     * @return Le compte bancaire trouvé
     * @throws BankAccountNotFoundException Si le compte n'existe pas
     */
    BankAccountDTO bankAccountById(String id) throws BankAccountNotFoundException;
    
    /**
     * Associe un client à un compte bancaire existant.
     * @param customerDTO Les données du client
     * @param id L'ID du compte bancaire
     * @return Le compte bancaire mis à jour
     * @throws BankAccountNotFoundException Si le compte n'existe pas
     */
    BankAccountDTO addCustomerToBankAccount(CustomerDTO customerDTO, String id) throws BankAccountNotFoundException;
    
    /**
     * Supprime un compte bancaire par son ID.
     * @param id L'ID du compte à supprimer
     * @throws BankAccountNotFoundException Si le compte n'existe pas
     */
    void  deleteBankAccount(String id) throws BankAccountNotFoundException;
    
    /**
     * Met à jour les informations d'un compte bancaire.
     * @param id L'ID du compte à mettre à jour
     * @param bankAccountDTO Les nouvelles données
     * @return Le compte bancaire mis à jour
     * @throws BankAccountNotFoundException Si le compte n'existe pas
     */
    BankAccountDTO updateBankAccount(String id, BankAccountDTO bankAccountDTO) throws BankAccountNotFoundException;
}
