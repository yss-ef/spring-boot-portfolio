package com.youssef.backend.services;

import com.youssef.backend.dtos.BankAccountDTO;
import com.youssef.backend.dtos.CurrentAccountDTO;
import com.youssef.backend.dtos.SavingAccountDTO;

import java.util.List;

public interface BankAccountService {
    // --- Service CurrentAccount ---
    CurrentAccountDTO saveCurrentAccount(CurrentAccountDTO currentAccountDTO);

    // --- Service SavingAccount ---
    SavingAccountDTO saveSavingAccount(SavingAccountDTO savingAccountDTO);

    // --- Service AccountOperation ---

    // --- Service BankAccount ---
    List<BankAccountDTO> listBankAccounts();
    List<BankAccountDTO> listBankAccountsByCustomerId(Long customerId);
}
