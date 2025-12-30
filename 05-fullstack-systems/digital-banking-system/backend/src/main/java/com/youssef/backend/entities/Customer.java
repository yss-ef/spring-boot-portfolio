package com.youssef.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Entité JPA représentant un client (Customer).
 * Mappée à la table "Customer" dans la base de données.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    
    /**
     * Liste des comptes bancaires associés à ce client.
     * Relation One-to-Many avec BankAccount.
     */
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) // l'appeler dans le DTOs mais utiliser BankAccountDTO qui lui ne renvoi pas le client
    private List<BankAccount> bankAccounts;
}
