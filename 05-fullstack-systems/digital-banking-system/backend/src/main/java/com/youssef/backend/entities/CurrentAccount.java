package com.youssef.backend.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entité JPA représentant un compte courant.
 * Hérite de BankAccount et ajoute la propriété de découvert (overDraft).
 * Identifié par la valeur "CUR" dans la colonne discriminante.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue(value = "CUR")
public class CurrentAccount extends BankAccount {
    private double overDraft;
}
