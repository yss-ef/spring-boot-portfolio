package com.youssef.backend.services;

import com.youssef.backend.dtos.AccountOperationDTO;

import java.util.List;

public interface AccountOperationService {
    AccountOperationDTO saveAccountOperation(AccountOperationDTO accountOperationDTO);
    List<AccountOperationDTO> listAccountOperations();
}
