package com.youssef.backend.mappers;

import com.youssef.backend.dtos.*;
import com.youssef.backend.entities.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapper {

    // --- Customer Mapper ---
    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    // --- CurrentAccount Mapper ---
    public CurrentAccountDTO fromCurrentAccount(CurrentAccount currentAccount){
        CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();

        BeanUtils.copyProperties(currentAccount, currentAccountDTO);
        if(currentAccount.getCustomer() != null){
            currentAccountDTO.setCustomerDTO(this.fromCustomer(currentAccount.getCustomer()));
        }

        return currentAccountDTO;
    }
    public  CurrentAccount fromCurrentAccountDTO(CurrentAccountDTO currentAccountDTO){
        CurrentAccount currentAccount = new CurrentAccount();

        BeanUtils.copyProperties(currentAccountDTO, currentAccount);
        if(currentAccountDTO.getCustomerDTO() != null){
            currentAccount.setCustomer(this.fromCustomerDTO(currentAccountDTO.getCustomerDTO()));
        }
        return currentAccount;
    }

    // --- SavingAccount Mapper ---
    public SavingAccountDTO fromSavingAccount(SavingAccount savingAccount ){
        SavingAccountDTO savingAccountDTO = new SavingAccountDTO();

        BeanUtils.copyProperties(savingAccount, savingAccountDTO);
        if(savingAccount.getCustomer() != null){
            savingAccountDTO.setCustomerDTO(this.fromCustomer(savingAccount.getCustomer()));
        }

        return savingAccountDTO;
    }
    public SavingAccount fromSavingAccountDTO(SavingAccountDTO savingAccountDTO){
        SavingAccount savingAccount = new SavingAccount();

        BeanUtils.copyProperties(savingAccountDTO, savingAccount);
        if(savingAccountDTO.getCustomerDTO() != null){
            savingAccount.setCustomer(this.fromCustomerDTO(savingAccountDTO.getCustomerDTO()));
        }
        return savingAccount;
    }

    // --- BankAccount Mapper ---
    public BankAccountDTO fromBankAccount(BankAccount bankAccount){
        if(bankAccount instanceof CurrentAccount){
            return this.fromCurrentAccount((CurrentAccount) bankAccount);
        }
        if (bankAccount instanceof SavingAccount){
            return this.fromSavingAccount((SavingAccount) bankAccount);
        }
        return null;
    }
    public BankAccount fromBankAccountDTO(BankAccountDTO bankAccountDTO){
        if(bankAccountDTO instanceof CurrentAccountDTO){
            return this.fromCurrentAccountDTO((CurrentAccountDTO) bankAccountDTO);
        }
        if (bankAccountDTO instanceof SavingAccountDTO){
            return this.fromSavingAccountDTO((SavingAccountDTO) bankAccountDTO);
        }
        return null;
    }

    // --- AccountOperation Mapper ---
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDTO accountOperationDTO = new AccountOperationDTO();

        BeanUtils.copyProperties(accountOperation, accountOperationDTO);
        if(accountOperation.getBankAccount() != null){
            accountOperationDTO.setBankAccountDTO(this.fromBankAccount(accountOperation.getBankAccount()));
        }
        return accountOperationDTO;
    }
    public AccountOperation fromAccountOperationDTO(AccountOperationDTO accountOperationDTO){
        AccountOperation accountOperation = new AccountOperation();

        BeanUtils.copyProperties(accountOperationDTO, accountOperation);
        if(accountOperationDTO.getBankAccountDTO() != null){
            accountOperation.setBankAccount(this.fromBankAccountDTO(accountOperationDTO.getBankAccountDTO()));

        }
        return accountOperation;
    }
}
