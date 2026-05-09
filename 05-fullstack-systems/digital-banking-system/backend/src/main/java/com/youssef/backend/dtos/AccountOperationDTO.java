package com.youssef.backend.dtos;

import com.youssef.backend.enums.OperationType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * DTO pour les opérations bancaires (Account Operation).
 * Contient les détails d'une opération comme le montant, la date, le type et le compte associé.
 */
@Getter @Setter
public class AccountOperationDTO {
    private Long id;
    private Date date;
    private double amount;
    private OperationType operationType;
    private BankAccountDTO bankAccountDTO;
    private String description;
}
