package com.youssef.backend.mappers;

import com.youssef.backend.dtos.SavingAccountRequestDTO;
import com.youssef.backend.dtos.SavingAccountResponseDTO;
import com.youssef.backend.entities.SavingAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SavingAccountMapper {
    SavingAccount fromRequest(SavingAccountRequestDTO savingAccountRequestDTO);
    SavingAccountResponseDTO fromEntity(SavingAccount savingAccount);
}
