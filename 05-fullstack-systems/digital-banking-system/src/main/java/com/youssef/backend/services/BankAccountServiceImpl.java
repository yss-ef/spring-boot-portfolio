package com.youssef.backend.services;

import com.youssef.backend.dtos.BankAccountDTO;
import com.youssef.backend.dtos.CurrentAccountDTO;
import com.youssef.backend.dtos.CustomerDTO;
import com.youssef.backend.dtos.SavingAccountDTO;
import com.youssef.backend.entities.BankAccount;
import com.youssef.backend.entities.CurrentAccount;
import com.youssef.backend.entities.SavingAccount;
import com.youssef.backend.exeptions.BankAccountNotFoundException;
import com.youssef.backend.mappers.BankAccountMapper;
import com.youssef.backend.repositories.BankAccountRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Implémentation du service de gestion des comptes bancaires.
 * Gère la logique métier pour les comptes courants et épargne.
 */
@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    BankAccountRepository bankAccountRepository;
    BankAccountMapper bankAccountMapper;

    @Override
    public CurrentAccountDTO saveCurrentAccount(CurrentAccountDTO currentAccountDTO) {
        CurrentAccount currentAccount = bankAccountMapper.fromCurrentAccountDTO(currentAccountDTO);
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreationDate(new Date());

        CurrentAccount savedCurrentAccount = bankAccountRepository.save(currentAccount);
        log.info("Current account saved successfully");
        return   bankAccountMapper.fromCurrentAccount(savedCurrentAccount);
    }

    @Override
    public SavingAccountDTO saveSavingAccount(SavingAccountDTO savingAccountDTO) {
        SavingAccount savingAccount = bankAccountMapper.fromSavingAccountDTO(savingAccountDTO);
        savingAccount.setCreationDate(new Date());
        savingAccount.setId(UUID.randomUUID().toString());
        SavingAccount savedSavingAccount = bankAccountRepository.save(savingAccount);
        log.info("Saving account saved successfully");
        return   bankAccountMapper.fromSavingAccount(savedSavingAccount);
    }

    @Override
    public List<BankAccountDTO> listBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        log.info("Bank accounts found successfully");
        return bankAccounts.stream().map(bankAccount -> bankAccountMapper
                .fromBankAccount(bankAccount)).toList();
    }

    @Override
    public List<BankAccountDTO> listBankAccountsByCustomerId(Long customerId) {
        List<BankAccount> bankAccounts = bankAccountRepository.findBankAccountsByCustomerId(customerId);
        return bankAccounts.stream().map(bankAccount -> bankAccountMapper.fromBankAccount(bankAccount)).toList();
    }

    @Override
    public BankAccountDTO bankAccountById(String id) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(id).orElseThrow(()-> new BankAccountNotFoundException("BankAccount not found"));
        return bankAccountMapper.fromBankAccount(bankAccount);
    }

    @Override
    public BankAccountDTO addCustomerToBankAccount(CustomerDTO customerDTO, String id) throws BankAccountNotFoundException {
        BankAccountDTO bankAccountDTO = this.bankAccountById(id);
        bankAccountDTO.setCustomerDTO(customerDTO);
        BankAccount bankAccount = bankAccountMapper.fromBankAccountDTO(bankAccountDTO);
        bankAccountRepository.save(bankAccount);
        log.info("Customer added to account successfully");
        return bankAccountDTO;
    }


    @Override
    public void deleteBankAccount(String id) throws BankAccountNotFoundException {
        if(!bankAccountRepository.existsById(id)){
            throw new BankAccountNotFoundException("BankAccount not found");
        }
        bankAccountRepository.deleteById(id);
        log.info("BankAccount deleted successfully");
    }

    @Override
    public BankAccountDTO updateBankAccount(String id, BankAccountDTO bankAccountDTO) throws BankAccountNotFoundException {
        BankAccount existingAccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new BankAccountNotFoundException("BankAccount not found"));

        if (bankAccountDTO.getBalance() != 0) {
            existingAccount.setBalance(bankAccountDTO.getBalance());
        }

        if (bankAccountDTO.getAccountStatus() != null) {
            existingAccount.setAccountStatus(bankAccountDTO.getAccountStatus());
        }

        if (bankAccountDTO.getCurrency() != null) {
            existingAccount.setCurrency(bankAccountDTO.getCurrency());
        }

        if (bankAccountDTO.getCustomerDTO() != null) {
            existingAccount.setCustomer(bankAccountMapper.fromCustomerDTO(bankAccountDTO.getCustomerDTO()));
        }

        if (existingAccount instanceof CurrentAccount && bankAccountDTO instanceof CurrentAccountDTO) {
            CurrentAccount currentAccount = (CurrentAccount) existingAccount;
            CurrentAccountDTO currentDTO = (CurrentAccountDTO) bankAccountDTO;

            if (currentDTO.getOverDraft() != 0) {
                currentAccount.setOverDraft(currentDTO.getOverDraft());
            }
        }
        else if (existingAccount instanceof SavingAccount && bankAccountDTO instanceof SavingAccountDTO) {
            SavingAccount savingAccount = (SavingAccount) existingAccount;
            SavingAccountDTO savingDTO = (SavingAccountDTO) bankAccountDTO;

            if (savingDTO.getInterestRate() != 0) {
                savingAccount.setInterestRate(savingDTO.getInterestRate());
            }
        }

        BankAccount savedBankAccount = bankAccountRepository.save(existingAccount);

        log.info("BankAccount updated successfully");
        return bankAccountMapper.fromBankAccount(savedBankAccount);
    }
}
