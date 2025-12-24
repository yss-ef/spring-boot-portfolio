package com.youssef.backend.services;

import com.youssef.backend.dtos.BankAccountDTO;
import com.youssef.backend.dtos.CurrentAccountDTO;
import com.youssef.backend.dtos.SavingAccountDTO;
import com.youssef.backend.entities.BankAccount;
import com.youssef.backend.entities.CurrentAccount;
import com.youssef.backend.entities.SavingAccount;
import com.youssef.backend.mappers.BankAccountMapper;
import com.youssef.backend.repositories.BankAccountRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    BankAccountRepository bankAccountRepository;
    BankAccountMapper bankAccountMapper;

    // --- Service CurrentAccount ---
    @Override
    public CurrentAccountDTO saveCurrentAccount(CurrentAccountDTO currentAccountDTO) {
        CurrentAccount currentAccount = bankAccountMapper.fromCurrentAccountDTO(currentAccountDTO);
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreationDate(new Date());

        CurrentAccount savedCurrentAccount = bankAccountRepository.save(currentAccount);
        log.info("Current account saved successfully");
        return   bankAccountMapper.fromCurrentAccount(savedCurrentAccount);
        // return bankAccountRepository.save(currentAccount);
    }

    // --- Service SavingAccount ---
    @Override
    public SavingAccountDTO saveSavingAccount(SavingAccountDTO savingAccountDTO) {
        SavingAccount savingAccount = bankAccountMapper.fromSavingAccountDTO(savingAccountDTO);
        savingAccount.setCreationDate(new Date());
        savingAccount.setId(UUID.randomUUID().toString());
        SavingAccount savedSavingAccount = bankAccountRepository.save(savingAccount);
        log.info("Saving account saved successfully");
        return   bankAccountMapper.fromSavingAccount(savedSavingAccount);
        //return bankAccountRepository.save(savingAccount);
    }

    // --- Service BankAccount ---
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

}
