package com.youssef.backend.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CurrentAccountResponseDTO extends BankAccountResponseDTO{
    private double overDraft;
}
