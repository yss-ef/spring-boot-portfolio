package com.youssef.springweb.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Pour hacher les mots de pass
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Strategie in memory authentification.
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                User.withUsername("admin").password(passwordEncoder().encode("123")).roles("ADMIN","USER").build(),
                User.withUsername("user1").password(passwordEncoder().encode("123")).roles("USER").build(),
                User.withUsername("user2").password(passwordEncoder().encode("123")).roles("USER").build()
        );
    }

    // Specifier les ressources et les authorization necessaire pour y acceder
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(fl->fl
                        .loginPage("/login")
                        .defaultSuccessUrl("/user/products",true)
                        .failureUrl("/login?error=true") // Renvoie vers login mais avec un message d'erreur dans l'url
                        .permitAll()
                )
                // L'ordre des règles d'autorisation est important.
                .authorizeHttpRequests(ar -> ar
                        .requestMatchers("/webjars/**", "/public/**", "/login").permitAll() // ressources publiques
                        .requestMatchers("/user/**").hasRole("USER") // Nécessite le rôle USER
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Nécessite le rôle ADMIN
                        .anyRequest().authenticated() // Toutes les autres requêtes nécessitent une authentification
                )
                .exceptionHandling(eh->eh.accessDeniedPage("/notAuthorized"))
                .build();
    }
}
