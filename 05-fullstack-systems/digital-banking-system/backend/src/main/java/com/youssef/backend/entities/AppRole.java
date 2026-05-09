package com.youssef.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

/**
 * Entité JPA représentant un rôle utilisateur (ex: ADMIN, USER).
 * Utilisé pour gérer les autorisations d'accès.
 */
@Entity
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppRole {
    @Id
    private String roleName; // Ex: "ADMIN", "USER"
}
