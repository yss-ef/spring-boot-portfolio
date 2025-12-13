package com.youssef.backend.mappers;

import com.youssef.backend.dtos.AccountOperationRequestDTO;
import com.youssef.backend.dtos.AccountOperationResponseDTO;
import com.youssef.backend.entities.AccountOperation;
import org.springframework.stereotype.Service;

@Service
public class AccountOperationMapper {
    public AccountOperation fromRequest(AccountOperationRequestDTO accountOperationRequestDTO){

        if(accountOperationRequestDTO == null){return null;}

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setAmount(accountOperationRequestDTO.getAmount());
        accountOperation.setOperationType(accountOperationRequestDTO.getOperationType());
        accountOperation.setDescription(accountOperationRequestDTO.getDescription());

        return accountOperation;
    };

    public AccountOperationResponseDTO fromEntity(AccountOperation accountOperation){

        if (accountOperation == null){return null;}

        AccountOperationResponseDTO accountOperationResponseDTO = new AccountOperationResponseDTO();
        accountOperationResponseDTO.setId(accountOperation.getId());
        accountOperationResponseDTO.setDate(accountOperation.getDate());
        accountOperationResponseDTO.setAmount(accountOperation.getAmount());
        accountOperationResponseDTO.setOperationType(accountOperation.getOperationType());
        accountOperationResponseDTO.setDescription(accountOperation.getDescription());

        if (accountOperation.getBankAccount() != null){
            accountOperationResponseDTO.setBankAccountId(accountOperation.getBankAccount().getId());
        }

        return accountOperationResponseDTO;
    };
}
