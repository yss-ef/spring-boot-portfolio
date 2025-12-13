package com.youssef.backend.dtos;

import com.youssef.backend.enums.AccountStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class BankAccountRequestDTO {
    private double balance;
    private AccountStatus accountStatus;
    private String currency;
    private String type;
    private String customerId;
}
