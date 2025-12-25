package com.youssef.backend.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO pour les comptes courants (Current Account).
 * Étend BankAccountDTO et ajoute la propriété de découvert (overDraft).
 */
@Getter
@Setter
public class CurrentAccountDTO extends BankAccountDTO {
    private double overDraft;
}
