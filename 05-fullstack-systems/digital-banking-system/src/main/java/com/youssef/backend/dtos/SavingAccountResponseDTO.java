package com.youssef.backend.dtos;

import com.youssef.backend.enums.AccountStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class SavingAccountResponseDTO{
    private String id;
    private Date creationDate;
    private double balance;
    private AccountStatus accountStatus;
    private String currency;
    private String customerId;
    private List<Long> accountOperationsId;
    private double interestRate;

}
