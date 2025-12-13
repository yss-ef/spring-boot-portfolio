package com.youssef.backend.mappers;

import com.youssef.backend.dtos.SavingAccountRequestDTO;
import com.youssef.backend.dtos.SavingAccountResponseDTO;
import com.youssef.backend.entities.AccountOperation;
import com.youssef.backend.entities.SavingAccount;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SavingAccountMapper {
    public SavingAccount fromRequest(SavingAccountRequestDTO savingAccountRequestDTO){

        if (savingAccountRequestDTO == null){return null;}

        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setBalance(savingAccountRequestDTO.getBalance());
        savingAccount.setAccountStatus(savingAccountRequestDTO.getAccountStatus());
        savingAccount.setCurrency(savingAccountRequestDTO.getCurrency());
        savingAccount.setInterestRate(savingAccountRequestDTO.getInterestRate());

        return savingAccount;
    };

    public SavingAccountResponseDTO fromEntity(SavingAccount savingAccount){

        if (savingAccount == null){return null;}

        SavingAccountResponseDTO savingAccountResponseDTO = new SavingAccountResponseDTO();
        savingAccountResponseDTO.setId(savingAccount.getId());
        savingAccountResponseDTO.setCreationDate(savingAccount.getCreationDate());
        savingAccountResponseDTO.setBalance(savingAccount.getBalance());
        savingAccountResponseDTO.setAccountStatus(savingAccount.getAccountStatus());
        savingAccountResponseDTO.setCurrency(savingAccount.getCurrency());
        savingAccountResponseDTO.setInterestRate(savingAccount.getInterestRate());

        if (savingAccount.getCustomer() != null){
            savingAccountResponseDTO.setCustomerId(savingAccount.getCustomer().getId());
        }

        savingAccountResponseDTO.setAccountOperationsId(new ArrayList<>());
        if (savingAccount.getAccountOperations() != null){
            for(AccountOperation accountOperation : savingAccount.getAccountOperations()){
                savingAccountResponseDTO.getAccountOperationsId().add(accountOperation.getId());
            }
        }

        return savingAccountResponseDTO;
    };
}
