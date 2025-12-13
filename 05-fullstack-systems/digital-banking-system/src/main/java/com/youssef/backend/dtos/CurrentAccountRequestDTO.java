package com.youssef.backend.dtos;

import com.youssef.backend.enums.AccountStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CurrentAccountRequestDTO{
    private double balance;
    private AccountStatus accountStatus;
    private String currency;
    private String customerId;
    private double overDraft;
}
