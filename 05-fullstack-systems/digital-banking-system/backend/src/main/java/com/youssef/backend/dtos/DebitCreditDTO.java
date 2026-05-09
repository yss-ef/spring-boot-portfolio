package com.youssef.backend.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilisé pour les opérations de débit et de crédit.
 * Contient l'ID du compte, le montant et la description de l'opération.
 */
@Getter
@Setter
public class DebitCreditDTO {
    private String accountId;
    private double amount;
    private String description;
}
