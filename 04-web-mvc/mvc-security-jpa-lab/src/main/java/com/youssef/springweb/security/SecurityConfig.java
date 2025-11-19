package com.youssef.springweb.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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
                .formLogin(Customizer.withDefaults())// .formLogin(Customizer.withDefaults(fl->fl.loginPage()
                // L'ordre ici est important.
                .authorizeHttpRequests(ar->ar.requestMatchers("/user/**").hasRole("USER")) // Les utilisateurs peuvent visionner la liste des produits
                .authorizeHttpRequests(ar->ar.requestMatchers("/admin/**").hasRole("ADMIN")) // Les administrateurs peuvent
                .authorizeHttpRequests(ar->ar.requestMatchers("/public/**").permitAll()) // ressources publique
                .authorizeHttpRequests(ar->ar.anyRequest().authenticated()) // Appliquer par default
                .build();
    }
}
