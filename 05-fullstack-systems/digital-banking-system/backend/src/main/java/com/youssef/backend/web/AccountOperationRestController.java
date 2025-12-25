package com.youssef.backend.web;

import com.youssef.backend.dtos.AccountOperationDTO;
import com.youssef.backend.dtos.DebitCreditDTO;
import com.youssef.backend.dtos.TransferRequestDTO;
import com.youssef.backend.exeptions.BalanceNotSufficientException;
import com.youssef.backend.exeptions.BankAccountNotFoundException;
import com.youssef.backend.services.AccountOperationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des opérations bancaires.
 * Expose les endpoints pour consulter les opérations, effectuer des débits, crédits et virements.
 */
@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/accounts")
public class AccountOperationRestController {

    private AccountOperationService accountOperationService;

    /**
     * Récupère toutes les opérations d'un compte spécifique.
     * @param accountId L'ID du compte
     * @return Liste des opérations du compte
     */
    @GetMapping("/{accountId}/operations")
    public List<AccountOperationDTO> getAllOperationByAccounts(@PathVariable String accountId){
        return accountOperationService.getAccountOperationByAccountId(accountId);
    }

    /**
     * Effectue une opération de débit.
     * @param request Les détails du débit (compte, montant, description)
     * @return L'opération de débit créée
     * @throws BankAccountNotFoundException Si le compte n'existe pas
     * @throws BalanceNotSufficientException Si le solde est insuffisant
     */
    @PostMapping("/debit")
    public AccountOperationDTO debit(@RequestBody DebitCreditDTO request) throws BankAccountNotFoundException, BalanceNotSufficientException {
        return accountOperationService.debit(request.getAccountId(), request.getAmount(), request.getDescription());
    }

    /**
     * Effectue une opération de crédit.
     * @param request Les détails du crédit (compte, montant, description)
     * @return L'opération de crédit créée
     * @throws BankAccountNotFoundException Si le compte n'existe pas
     */
    @PostMapping("/credit")
    public AccountOperationDTO credit(@RequestBody DebitCreditDTO request) throws BankAccountNotFoundException {
        return accountOperationService.credit(request.getAccountId(), request.getAmount(), request.getDescription());
    }

    /**
     * Effectue un virement entre deux comptes.
     * @param request Les détails du virement (comptes source/destination, montant)
     * @throws BankAccountNotFoundException Si l'un des comptes n'existe pas
     * @throws BalanceNotSufficientException Si le solde du compte source est insuffisant
     */
    @PostMapping("/transfer")
    public void transfer(@RequestBody TransferRequestDTO request) throws BankAccountNotFoundException, BalanceNotSufficientException {
        accountOperationService.transfer(
                request.getAccountSource(),
                request.getAccountDestination(),
                request.getAmount()
        );
    }
}
