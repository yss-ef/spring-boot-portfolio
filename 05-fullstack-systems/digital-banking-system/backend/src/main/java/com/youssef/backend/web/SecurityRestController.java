package com.youssef.backend.web; // Vérifiez votre package

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion de la sécurité et de l'authentification.
 * Fournit des endpoints pour se connecter et récupérer le profil utilisateur.
 */
@RestController
@RequestMapping("/auth")
public class SecurityRestController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtEncoder jwtEncoder;

    /**
     * Récupère les informations de l'utilisateur authentifié.
     * @param authentication L'objet Authentication injecté par Spring Security
     * @return Les détails de l'authentification (principal, autorités, etc.)
     */
    @GetMapping("/profile")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }

    /**
     * Authentifie un utilisateur et génère un token JWT.
     * @param credentials Map contenant le nom d'utilisateur et le mot de passe
     * @return Map contenant le token d'accès JWT ("access-token")
     */
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        Instant instant = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(10, ChronoUnit.MINUTES))
                .subject(username)
                .claim("scope", scope)
                .build();
        JwtEncoderParameters jwtEncoderParameters =
                JwtEncoderParameters.from(
                        JwsHeader.with(MacAlgorithm.HS512).build(),
                        jwtClaimsSet
                );
        String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
        return Map.of("access-token", jwt);
    }
}
