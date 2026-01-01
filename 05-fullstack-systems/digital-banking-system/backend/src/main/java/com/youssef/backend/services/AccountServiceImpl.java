package com.youssef.backend.services;

import com.youssef.backend.entities.AppRole;
import com.youssef.backend.entities.AppUser;
import com.youssef.backend.repositories.AppRoleRepository;
import com.youssef.backend.repositories.AppUserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation du service de gestion des comptes utilisateurs.
 * Implémente également UserDetailsService pour l'intégration avec Spring Security.
 */
@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    PasswordEncoder passwordEncoder;

    /**
     * Charge un utilisateur par son nom d'utilisateur pour Spring Security.
     * @param username Le nom d'utilisateur
     * @return UserDetails contenant les informations de l'utilisateur et ses rôles
     * @throws UsernameNotFoundException Si l'utilisateur n'est pas trouvé
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser == null) throw new UsernameNotFoundException("user not found");

        List<SimpleGrantedAuthority> authorities = appUser.getRoles().stream()
                .map(appRole -> new SimpleGrantedAuthority(appRole.getRoleName()))
                .toList();
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }

    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        AppUser user = appUserRepository.findByUsername(username);
        if(user != null) throw new RuntimeException("Cet utilisateur existe déjà");
        if(!password.equals(confirmPassword)) throw new RuntimeException("Les mots de passe ne correspondent pas");

        AppUser newUser = AppUser.builder()
                .username(username)
                .password(passwordEncoder.encode(password)) // Toujours encoder le MDP !
                .email(email)
                .roles(new ArrayList<>())
                .build();
        return appUserRepository.save(newUser);
    }

    @Override
    public AppRole addNewRole(String role) {
        return appRoleRepository.save(AppRole.builder().roleName(role).build());
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser user = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        user.getRoles().add(appRole);
    }
}
