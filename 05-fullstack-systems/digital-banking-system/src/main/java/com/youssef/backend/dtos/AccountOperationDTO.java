package com.youssef.backend.dtos;

import com.youssef.backend.entities.BankAccount;
import com.youssef.backend.enums.OperationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class AccountOperationDTO {
    private Long id;
    private Date date;
    private double amount;
    private OperationType operationType;
    private BankAccountDTO bankAccountDTO;
    private String description;
}
