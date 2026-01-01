package com.youssef.backend.repositories;

import com.youssef.backend.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username); // Méthode cruciale pour Spring Security
}