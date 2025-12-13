package com.youssef.backend.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CurrentAccountRequestDTO extends BankAccountRequestDTO{
    private double overDraft;
}
