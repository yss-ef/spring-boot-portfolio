package com.youssef.backend.repositories;

import com.youssef.backend.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository JPA pour l'entité AppRole.
 * Permet de gérer les rôles des utilisateurs.
 */
public interface AppRoleRepository extends JpaRepository<AppRole, String> {

}
