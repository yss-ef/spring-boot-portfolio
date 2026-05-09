package com.youssef.backend.repositories;

import com.youssef.backend.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository JPA pour l'entité AppUser.
 * Permet de gérer les utilisateurs de l'application.
 */
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    /**
     * Trouve un utilisateur par son nom d'utilisateur.
     * Essentiel pour l'authentification Spring Security.
     * @param username Le nom d'utilisateur
     * @return L'utilisateur trouvé ou null
     */
    AppUser findByUsername(String username);
}
