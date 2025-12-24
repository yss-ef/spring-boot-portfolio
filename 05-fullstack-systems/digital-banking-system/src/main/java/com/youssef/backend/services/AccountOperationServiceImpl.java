package com.youssef.backend.services;

import com.youssef.backend.dtos.AccountOperationDTO;
import com.youssef.backend.entities.AccountOperation;
import com.youssef.backend.mappers.BankAccountMapper;
import com.youssef.backend.repositories.AccountOperationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AccountOperationServiceImpl implements AccountOperationService {

    BankAccountMapper bankAccountMapper;
    AccountOperationRepository accountOperationRepository;

    @Override
    public AccountOperationDTO saveAccountOperation(AccountOperationDTO accountOperationDTO){
        AccountOperation accountOperation = bankAccountMapper.fromAccountOperationDTO(accountOperationDTO);
        AccountOperation savedAccountOperation = accountOperationRepository.save(accountOperation);
        log.info("Account operation saved successfully");
        return  bankAccountMapper.fromAccountOperation(savedAccountOperation);
    }

    @Override
    public List<AccountOperationDTO> listAccountOperations() {
        List<AccountOperation> accountOperations = accountOperationRepository.findAll();
        log.info("Account operations found successfully");
        return accountOperations.stream().map(accountOperation -> bankAccountMapper
                .fromAccountOperation(accountOperation)).toList();
    }
}
