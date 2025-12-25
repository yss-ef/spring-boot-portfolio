package com.youssef.backend.web;

import com.youssef.backend.dtos.BankAccountDTO;
import com.youssef.backend.dtos.CurrentAccountDTO;
import com.youssef.backend.dtos.SavingAccountDTO;
import com.youssef.backend.exeptions.BankAccountNotFoundException;
import com.youssef.backend.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des comptes bancaires.
 * Expose les endpoints pour créer, lire, mettre à jour et supprimer des comptes.
 */
@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/accounts")
public class BankAccountRestController {

    private BankAccountService bankAccountService;

    /**
     * Récupère tous les comptes bancaires.
     * @return Liste des comptes
     */
    @GetMapping("")
    public List<BankAccountDTO> getAllBankAccounts(){
        return bankAccountService.listBankAccounts();
    }

    /**
     * Récupère un compte bancaire par son ID.
     * @param id L'ID du compte
     * @return Le compte trouvé
     * @throws BankAccountNotFoundException Si le compte n'existe pas
     */
    @GetMapping("/{id}")
    public BankAccountDTO getBankAccountById(@PathVariable String id) throws BankAccountNotFoundException {
        return bankAccountService.bankAccountById(id);
    }

    /**
     * Récupère les comptes bancaires d'un client spécifique.
     * @param customerId L'ID du client
     * @return Liste des comptes du client
     */
    @GetMapping("/customer/{customerId}")
    public List<BankAccountDTO> getBankAccountsByCustomerId(@PathVariable Long customerId){
        return bankAccountService.listBankAccountsByCustomerId(customerId);
    }

    /**
     * Crée un nouveau compte courant.
     * @param currentAccountDTO Les données du compte courant
     * @return Le compte courant créé
     */
    @PostMapping("/current")
    public CurrentAccountDTO saveCurrentAccount(@RequestBody CurrentAccountDTO currentAccountDTO){
        return bankAccountService.saveCurrentAccount(currentAccountDTO);
    }

    /**
     * Crée un nouveau compte épargne.
     * @param savingAccountDTO Les données du compte épargne
     * @return Le compte épargne créé
     */
    @PostMapping("/saving")
    public SavingAccountDTO saveSavingAccount(@RequestBody SavingAccountDTO savingAccountDTO){
        return bankAccountService.saveSavingAccount(savingAccountDTO);
    }

    /**
     * Met à jour un compte bancaire.
     * @param id L'ID du compte à mettre à jour
     * @param bankAccountDTO Les nouvelles données
     * @return Le compte mis à jour
     * @throws BankAccountNotFoundException Si le compte n'existe pas
     */
    @PutMapping("/{id}")
    public BankAccountDTO updateBankAccount(@PathVariable String id, @RequestBody BankAccountDTO bankAccountDTO) throws BankAccountNotFoundException {
        bankAccountDTO.setId(id);
        return bankAccountService.updateBankAccount(id, bankAccountDTO);
    }

    /**
     * Supprime un compte bancaire.
     * @param id L'ID du compte à supprimer
     * @throws BankAccountNotFoundException Si le compte n'existe pas
     */
    @DeleteMapping("/{id}")
    public void deleteBankAccount(@PathVariable String id) throws BankAccountNotFoundException {
        bankAccountService.deleteBankAccount(id);
    }
}
