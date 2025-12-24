package com.youssef.backend.services;

import com.youssef.backend.dtos.*;
import com.youssef.backend.entities.CurrentAccount;
import com.youssef.backend.entities.Customer;
import com.youssef.backend.entities.SavingAccount;

import java.util.List;

public interface BankService {

    // --- Service Customer ---
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    List<CustomerDTO> listCustomers();

    // --- Service CurrentAccount ---
    CurrentAccountDTO saveCurrentAccount(CurrentAccountDTO currentAccountDTO);

    // --- Service SavingAccount ---
    SavingAccountDTO saveSavingAccount(SavingAccountDTO savingAccountDTO);

    // --- Service AccountOperation ---
    AccountOperationDTO saveAccountOperation(AccountOperationDTO accountOperationDTO);
    List<AccountOperationDTO> listAccountOperations();
    // --- Service BankAccount ---
    List<BankAccountDTO> listBankAccounts();

}

