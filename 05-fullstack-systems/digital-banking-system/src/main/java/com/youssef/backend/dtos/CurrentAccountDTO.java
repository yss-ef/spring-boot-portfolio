package com.youssef.backend.dtos;

import com.youssef.backend.entities.BankAccount;
import com.youssef.backend.entities.Customer;
import com.youssef.backend.enums.AccountStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

// Creation de DTO, pour le BackendApplication
public class CurrentAccountDTO extends BankAccountDTO {
    private double overDraft;
}
