package com.youssef.backend.services;

import com.youssef.backend.dtos.*;
import com.youssef.backend.entities.*;
import com.youssef.backend.mappers.BankAccountMapper;
import com.youssef.backend.repositories.AccountOperationRepository;
import com.youssef.backend.repositories.BankAccountRepository;
import com.youssef.backend.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class BankServiceImpl implements BankService {
    CustomerRepository customerRepository;
    BankAccountRepository bankAccountRepository;
    BankAccountMapper bankAccountMapper;
    AccountOperationRepository accountOperationRepository;

    // --- Service Customer ---
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer = bankAccountMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return   bankAccountMapper.fromCustomer(savedCustomer);
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customer -> bankAccountMapper
                .fromCustomer(customer))
                .toList();
    }


    // --- Service CurrentAccount ---
    @Override
    public CurrentAccountDTO saveCurrentAccount(CurrentAccountDTO currentAccountDTO) {
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount = bankAccountMapper.fromCurrentAccountDTO(currentAccountDTO);
        CurrentAccount savedCurrentAccount = bankAccountRepository.save(currentAccount);
        return   bankAccountMapper.fromCurrentAccount(savedCurrentAccount);
        // return bankAccountRepository.save(currentAccount);
    }

    // --- Service SavingAccount ---
    @Override
    public SavingAccountDTO saveSavingAccount(SavingAccountDTO savingAccountDTO) {
        SavingAccount savingAccount = new SavingAccount();
        savingAccount = bankAccountMapper.fromSavingAccountDTO(savingAccountDTO);
        SavingAccount savedSavingAccount = bankAccountRepository.save(savingAccount);
        return   bankAccountMapper.fromSavingAccount(savedSavingAccount);
        //return bankAccountRepository.save(savingAccount);
    }

    // --- Service AccountOperation ---
    @Override
    public AccountOperationDTO saveAccountOperation(AccountOperationDTO accountOperationDTO){
        AccountOperation accountOperation = new AccountOperation();
        accountOperation = bankAccountMapper.fromAccountOperationDTO(accountOperationDTO);
        AccountOperation savedAccountOperation = accountOperationRepository.save(accountOperation);
        return  bankAccountMapper.fromAccountOperation(savedAccountOperation);
    }

    @Override
    public List<AccountOperationDTO> listAccountOperations() {
        List<AccountOperation> accountOperations = accountOperationRepository.findAll();
        return accountOperations.stream().map(accountOperation -> bankAccountMapper
                .fromAccountOperation(accountOperation)).toList();
    }

    // --- Service BankAccount ---
    @Override
    public List<BankAccountDTO> listBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        return bankAccounts.stream().map(bankAccount -> bankAccountMapper
                        .fromBankAccount(bankAccount)).toList();
    }
}
