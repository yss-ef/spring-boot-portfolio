package com.youssef.backend.entities;

import com.youssef.backend.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Entité JPA abstraite représentant un compte bancaire.
 * Utilise la stratégie d'héritage SINGLE_TABLE pour stocker tous les types de comptes dans une seule table.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", length = 4)
public abstract class BankAccount {
    @Id
    private String id;
    private Date creationDate;
    private double balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    private String currency;
    
    /**
     * Client propriétaire du compte.
     * Relation Many-to-One.
     */
    @ManyToOne // ne pas utiliser les relations @ManyToMany dans les DTOs
    private Customer customer;
    
    /**
     * Liste des opérations effectuées sur ce compte.
     * Relation One-to-Many.
     */
    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<AccountOperation> accountOperations;

    @Column(name = "type", insertable = false, updatable = false)
    private String type;
}
