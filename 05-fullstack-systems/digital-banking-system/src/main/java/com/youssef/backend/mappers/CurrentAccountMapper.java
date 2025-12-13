package com.youssef.backend.mappers;

import com.youssef.backend.dtos.CurrentAccountRequestDTO;
import com.youssef.backend.dtos.CurrentAccountResponseDTO;
import com.youssef.backend.entities.AccountOperation;
import com.youssef.backend.entities.CurrentAccount;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CurrentAccountMapper {

    public CurrentAccount fromRequest(CurrentAccountRequestDTO currentAccountRequestDTO){

        if (currentAccountRequestDTO == null){return null;}

        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setOverDraft(currentAccountRequestDTO.getOverDraft());
        currentAccount.setBalance(currentAccountRequestDTO.getBalance());
        currentAccount.setAccountStatus(currentAccountRequestDTO.getAccountStatus());
        currentAccount.setCurrency(currentAccountRequestDTO.getCurrency());

        return currentAccount;
    };

    public CurrentAccountResponseDTO fromEntity(CurrentAccount currentAccount){

        if (currentAccount == null){return null;}

        CurrentAccountResponseDTO currentAccountResponseDTO = new CurrentAccountResponseDTO();
        currentAccountResponseDTO.setId(currentAccount.getId());
        currentAccountResponseDTO.setCreationDate(currentAccount.getCreationDate());
        currentAccountResponseDTO.setBalance(currentAccount.getBalance());
        currentAccountResponseDTO.setAccountStatus(currentAccount.getAccountStatus());
        currentAccountResponseDTO.setCurrency(currentAccount.getCurrency());
        currentAccountResponseDTO.setOverDraft(currentAccount.getOverDraft());

        if (currentAccount.getCustomer() != null){
            currentAccountResponseDTO.setCustomerId(currentAccount.getCustomer().getId());
        }

        currentAccountResponseDTO.setAccountOperationsId(new ArrayList<>());
        if (currentAccount.getAccountOperations() != null){
            for(AccountOperation accountOperation : currentAccount.getAccountOperations()){
                currentAccountResponseDTO.getAccountOperationsId().add(accountOperation.getId());
            }
        }
        return currentAccountResponseDTO;
    };
}
