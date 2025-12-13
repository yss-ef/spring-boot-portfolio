package com.youssef.backend.mappers;

import com.youssef.backend.dtos.AccountOperationRequestDTO;
import com.youssef.backend.dtos.AccountOperationResponseDTO;
import com.youssef.backend.entities.AccountOperation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountOperationMapper {
    AccountOperation fromRequest(AccountOperationRequestDTO accountOperationRequestDTO);
    AccountOperationResponseDTO fromEntity(AccountOperation accountOperation);
}
