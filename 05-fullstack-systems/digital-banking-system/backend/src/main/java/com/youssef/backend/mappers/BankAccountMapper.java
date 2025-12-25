package com.youssef.backend.mappers;

import com.youssef.backend.dtos.*;
import com.youssef.backend.entities.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * Service de mapping pour convertir les entités en DTOs et vice-versa.
 * Utilise BeanUtils pour copier les propriétés.
 */
@Service
public class BankAccountMapper {

    // --- Customer Mapper ---
    
    /**
     * Convertit une entité Customer en CustomerDTO.
     */
    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }

    /**
     * Convertit un CustomerDTO en entité Customer.
     */
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    // --- CurrentAccount Mapper ---
    
    /**
     * Convertit une entité CurrentAccount en CurrentAccountDTO.
     */
    public CurrentAccountDTO fromCurrentAccount(CurrentAccount currentAccount){
        CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();

        BeanUtils.copyProperties(currentAccount, currentAccountDTO);
        if(currentAccount.getCustomer() != null){
            currentAccountDTO.setCustomerDTO(this.fromCustomer(currentAccount.getCustomer()));
        }

        return currentAccountDTO;
    }

    /**
     * Convertit un CurrentAccountDTO en entité CurrentAccount.
     */
    public  CurrentAccount fromCurrentAccountDTO(CurrentAccountDTO currentAccountDTO){
        CurrentAccount currentAccount = new CurrentAccount();

        BeanUtils.copyProperties(currentAccountDTO, currentAccount);
        if(currentAccountDTO.getCustomerDTO() != null){
            currentAccount.setCustomer(this.fromCustomerDTO(currentAccountDTO.getCustomerDTO()));
        }
        return currentAccount;
    }

    // --- SavingAccount Mapper ---
    
    /**
     * Convertit une entité SavingAccount en SavingAccountDTO.
     */
    public SavingAccountDTO fromSavingAccount(SavingAccount savingAccount ){
        SavingAccountDTO savingAccountDTO = new SavingAccountDTO();

        BeanUtils.copyProperties(savingAccount, savingAccountDTO);
        if(savingAccount.getCustomer() != null){
            savingAccountDTO.setCustomerDTO(this.fromCustomer(savingAccount.getCustomer()));
        }

        return savingAccountDTO;
    }

    /**
     * Convertit un SavingAccountDTO en entité SavingAccount.
     */
    public SavingAccount fromSavingAccountDTO(SavingAccountDTO savingAccountDTO){
        SavingAccount savingAccount = new SavingAccount();

        BeanUtils.copyProperties(savingAccountDTO, savingAccount);
        if(savingAccountDTO.getCustomerDTO() != null){
            savingAccount.setCustomer(this.fromCustomerDTO(savingAccountDTO.getCustomerDTO()));
        }
        return savingAccount;
    }

    // --- BankAccount Mapper ---
    
    /**
     * Convertit une entité BankAccount (Current ou Saving) en BankAccountDTO correspondant.
     */
    public BankAccountDTO fromBankAccount(BankAccount bankAccount){
        if(bankAccount instanceof CurrentAccount){
            return this.fromCurrentAccount((CurrentAccount) bankAccount);
        }
        if (bankAccount instanceof SavingAccount){
            return this.fromSavingAccount((SavingAccount) bankAccount);
        }
        return null;
    }

    /**
     * Convertit un BankAccountDTO (Current ou Saving) en entité BankAccount correspondante.
     */
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
    
    /**
     * Convertit une entité AccountOperation en AccountOperationDTO.
     */
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDTO accountOperationDTO = new AccountOperationDTO();

        BeanUtils.copyProperties(accountOperation, accountOperationDTO);
        if(accountOperation.getBankAccount() != null){
            accountOperationDTO.setBankAccountDTO(this.fromBankAccount(accountOperation.getBankAccount()));
        }
        return accountOperationDTO;
    }

    /**
     * Convertit un AccountOperationDTO en entité AccountOperation.
     */
    public AccountOperation fromAccountOperationDTO(AccountOperationDTO accountOperationDTO){
        AccountOperation accountOperation = new AccountOperation();

        BeanUtils.copyProperties(accountOperationDTO, accountOperation);
        if(accountOperationDTO.getBankAccountDTO() != null){
            accountOperation.setBankAccount(this.fromBankAccountDTO(accountOperationDTO.getBankAccountDTO()));

        }
        return accountOperation;
    }
}
