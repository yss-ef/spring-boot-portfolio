package com.youssef.backend.services;

import com.youssef.backend.dtos.AccountOperationDTO;
import com.youssef.backend.entities.AccountOperation;
import com.youssef.backend.entities.BankAccount;
import com.youssef.backend.entities.CurrentAccount;
import com.youssef.backend.entities.SavingAccount;
import com.youssef.backend.exeptions.BalanceNotSufficientException;
import com.youssef.backend.exeptions.BankAccountNotFoundException;
import com.youssef.backend.mappers.BankAccountMapper;
import com.youssef.backend.repositories.AccountOperationRepository;
import com.youssef.backend.repositories.BankAccountRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.youssef.backend.enums.OperationType.CREDIT;
import static com.youssef.backend.enums.OperationType.DEBIT;

/**
 * Implémentation du service de gestion des opérations bancaires.
 * Gère les débits, crédits et virements avec les vérifications nécessaires (solde, existence du compte).
 */
@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AccountOperationServiceImpl implements AccountOperationService {

    private BankAccountMapper bankAccountMapper;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountRepository bankAccountRepository;

    @Override
    public AccountOperationDTO saveAccountOperation(AccountOperationDTO accountOperationDTO){
        AccountOperation accountOperation = bankAccountMapper.fromAccountOperationDTO(accountOperationDTO);
        accountOperation.setDate(new Date());
        AccountOperation savedAccountOperation = accountOperationRepository.save(accountOperation);
        log.info("Account operation saved successfully");
        return  bankAccountMapper.fromAccountOperation(savedAccountOperation);
    }

    @Override
    public List<AccountOperationDTO> listAccountOperations() {
        List<AccountOperation> accountOperations = accountOperationRepository.findAll();
        log.info("Account operations found successfully");
        return accountOperations.stream().map(accountOperation -> bankAccountMapper
                .fromAccountOperation(accountOperation)).toList();
    }

    @Override
    public List<AccountOperationDTO> getAccountOperationByAccountId(String bankAccountId){
        List<AccountOperation> accountOperations = accountOperationRepository.findAccountOperationByBankAccountId(bankAccountId);
        return accountOperations.stream().map( accountOperation -> bankAccountMapper
                .fromAccountOperation(accountOperation)).toList();
    }

    @Override
    public AccountOperationDTO debit(String bankAccountId, double amount, String desc) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId).orElseThrow(() -> new BankAccountNotFoundException("Bank account not found"));

        AccountOperation newAccountOperation = new AccountOperation();
        newAccountOperation.setAmount(amount);
        newAccountOperation.setBankAccount(bankAccount);
        newAccountOperation.setDescription(desc);
        newAccountOperation.setDate(new Date());

        if(bankAccount instanceof SavingAccount){
            if (bankAccount.getBalance() < amount) {
                throw new BalanceNotSufficientException("Amount not sufficient");
            }
            newAccountOperation.setOperationType(DEBIT);
            bankAccount.setBalance(bankAccount.getBalance() - amount);
            bankAccountRepository.save(bankAccount);
        }

        if(bankAccount instanceof CurrentAccount){
            if ((bankAccount.getBalance() +  ((CurrentAccount) bankAccount).getOverDraft()) < amount) {
                throw new BalanceNotSufficientException("Amount not sufficient");
            }
            newAccountOperation.setOperationType(DEBIT);
            bankAccount.setBalance(bankAccount.getBalance() - amount);
            bankAccountRepository.save(bankAccount);
        }

        AccountOperation savedAccountOperation = accountOperationRepository.save(newAccountOperation);

        log.info("Debit operation saved successfully");

        return bankAccountMapper.fromAccountOperation(savedAccountOperation);
    }

    @Override
    public AccountOperationDTO credit(String bankAccountId, double amount, String desc) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId).orElseThrow(() -> new BankAccountNotFoundException("Bank account not found"));
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);

        AccountOperation newAccountOperation = new AccountOperation();
        newAccountOperation.setAmount(amount);
        newAccountOperation.setBankAccount(bankAccount);
        newAccountOperation.setDescription(desc);
        newAccountOperation.setDate(new Date());
        newAccountOperation.setOperationType(CREDIT);

        AccountOperation savedAccountOperation = accountOperationRepository.save(newAccountOperation);

        log.info("Credit operation saved successfully");

        return bankAccountMapper.fromAccountOperation(savedAccountOperation);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountIdSource, amount, "Transfer to " + accountIdDestination);
        credit(accountIdDestination, amount, "Transfer from " + accountIdSource);

        log.info("Transfer successfully");
    }
}
