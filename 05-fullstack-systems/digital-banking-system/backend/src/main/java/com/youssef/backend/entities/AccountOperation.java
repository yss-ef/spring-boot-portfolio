package com.youssef.backend.entities;

import com.youssef.backend.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Entité JPA représentant une opération bancaire (débit ou crédit).
 * Liée à un compte bancaire spécifique.
 */
@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class AccountOperation{
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType operationType;
    @ManyToOne()
    private BankAccount bankAccount;
    private String description;
}
