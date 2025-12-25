package com.youssef.backend.dtos;

import com.youssef.backend.enums.AccountStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Classe abstraite DTO pour les comptes bancaires.
 * Sert de base pour CurrentAccountDTO et SavingAccountDTO.
 */
@Getter
@Setter
public abstract class BankAccountDTO {
    private String id;
    private Date creationDate;
    private double balance;
    private AccountStatus accountStatus;
    private String currency;
    private CustomerDTO customerDTO;
    private String type;
}
