package com.youssef.backend.dtos;

import com.youssef.backend.entities.BankAccount;
import com.youssef.backend.entities.Customer;
import com.youssef.backend.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class SavingAccountDTO extends BankAccountDTO {
    private double interestRate;
}
