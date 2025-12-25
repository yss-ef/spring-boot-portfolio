package com.youssef.backend.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entité JPA représentant un compte épargne.
 * Hérite de BankAccount et ajoute le taux d'intérêt (interestRate).
 * Identifié par la valeur "SAV" dans la colonne discriminante.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue(value = "SAV")
public class SavingAccount extends BankAccount {
    private double interestRate;
}
