package com.youssef.backend.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class SavingAccountResponseDTO extends  BankAccountResponseDTO{
    private double interestRate;

}
