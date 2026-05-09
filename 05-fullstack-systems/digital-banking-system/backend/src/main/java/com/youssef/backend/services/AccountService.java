package com.youssef.backend.services;

import com.youssef.backend.entities.AppRole;
import com.youssef.backend.entities.AppUser;

/**
 * Interface définissant les services de gestion des utilisateurs et des rôles.
 * Utilisée pour l'authentification et l'autorisation.
 */
public interface AccountService {
    /**
     * Crée un nouvel utilisateur.
     * @param username Nom d'utilisateur
     * @param password Mot de passe
     * @param email Email de l'utilisateur
     * @param confirmPassword Confirmation du mot de passe
     * @return L'utilisateur créé
     */
    AppUser addNewUser(String username, String password, String email, String confirmPassword);

    /**
     * Crée un nouveau rôle.
     * @param role Nom du rôle (ex: "ADMIN", "USER")
     * @return Le rôle créé
     */
    AppRole addNewRole(String role);

    /**
     * Associe un rôle à un utilisateur.
     * @param username Nom d'utilisateur
     * @param role Nom du rôle
     */
    void addRoleToUser(String username, String role);
}
