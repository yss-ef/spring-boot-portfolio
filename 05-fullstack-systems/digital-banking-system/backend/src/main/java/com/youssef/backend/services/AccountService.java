package com.youssef.backend.services;

import com.youssef.backend.entities.AppRole;
import com.youssef.backend.entities.AppUser;

public interface AccountService {
    AppUser addNewUser(String username, String password, String email, String confirmPassword);

    // 3. Méthode pour créer un rôle
    AppRole addNewRole(String role);

    // 4. Méthode pour associer User et Role
    void addRoleToUser(String username, String role);
}
