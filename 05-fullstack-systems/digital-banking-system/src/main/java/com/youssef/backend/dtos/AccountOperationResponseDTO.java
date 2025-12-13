package com.youssef.backend.dtos;

import com.youssef.backend.enums.OperationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class AccountOperationResponseDTO {
    private Long id;
    private Date date;
    private double amount;
    private OperationType operationType;
    private String description;
    private String bankAccountId;
}
