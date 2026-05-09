package com.youssef.backend.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO pour les comptes épargne (Saving Account).
 * Étend BankAccountDTO et ajoute le taux d'intérêt (interestRate).
 */
@Getter
@Setter
public class SavingAccountDTO extends BankAccountDTO {
    private double interestRate;
}
