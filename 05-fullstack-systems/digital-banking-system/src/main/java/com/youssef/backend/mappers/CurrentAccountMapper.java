package com.youssef.backend.mappers;

import com.youssef.backend.dtos.CurrentAccountRequestDTO;
import com.youssef.backend.dtos.CurrentAccountResponseDTO;
import com.youssef.backend.entities.CurrentAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrentAccountMapper {
    CurrentAccount fromRequest(CurrentAccountRequestDTO currentAccountRequestDTO);
    CurrentAccountResponseDTO fromEntity(CurrentAccount currentAccount);
}
