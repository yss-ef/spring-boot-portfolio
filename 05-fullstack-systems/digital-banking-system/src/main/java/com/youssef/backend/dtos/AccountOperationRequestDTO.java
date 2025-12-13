package com.youssef.backend.dtos;

import com.youssef.backend.enums.OperationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class AccountOperationRequestDTO {
    private double amount;
    private OperationType operationType;
    private String description;
    private String bankAccountId;
}
