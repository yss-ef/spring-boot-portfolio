# Compte Rendu du TP 3 : Application Web avec Spring Boot, Spring Security et JPA

Ce document est le rapport du troisième travail pratique, axé sur le développement d'une application web complète de gestion de produits. L'objectif était de mettre en œuvre une architecture multicouche en utilisant **Spring Boot**, avec une persistance des données gérée par **Spring Data JPA** et une sécurisation des accès via **Spring Security**.

---

## 1. Fonctionnalités de l'Application

L'application permet de réaliser les opérations CRUD (Create, Read, Update, Delete) sur une entité "Produit". Les accès sont restreints par rôles, démontrant une gestion des autorisations simple mais efficace :

*   **Utilisateurs (rôle `USER`) :** Peuvent consulter la liste des produits.
*   **Administrateurs (rôle `ADMIN`) :** Disposent des droits complets et peuvent consulter, ajouter, modifier et supprimer des produits.
*   Une page de connexion standard fournie par Spring Security permet aux utilisateurs de s'authentifier.

---

## 2. Technologies Utilisées

Le projet s'appuie sur l'écosystème Spring et d'autres technologies standards du développement web Java :

*   **Backend :** Spring Boot, Spring MVC, Spring Data JPA, Spring Security
*   **Frontend :** Thymeleaf, Thymeleaf Layout Dialect, Bootstrap 5
*   **Base de données :** H2 (base de données en mémoire pour le développement)
*   **Build Tool :** Maven
*   **Serveur d'application :** Tomcat (embarqué par défaut dans Spring Boot)

---

## 3. Détails de l'Implémentation

L'architecture de l'application est divisée en plusieurs couches logiques : entités, repositories, services, et contrôleurs.

### Couche de Persistance (JPA)

#### Entité `Product.java`
La classe `Product` représente les données des produits qui seront stockées en base de données. Elle utilise des annotations de validation (`@NotEmpty`, `@Min`) pour assurer l'intégrité des données.

```java
package com.youssef.springweb.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Size(min = 3, max = 50)
    private String name;
    @Min(1)
    private double price;
    private int quantity;
}
```

#### Repository `ProductRepository.java`
L'interface `ProductRepository`, qui étend `JpaRepository`, permet de bénéficier sans effort des opérations CRUD et de requêtes plus complexes.

```java
package com.youssef.springweb.reposetories;

import com.youssef.springweb.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
```

### Couche Web (Controller)

Le `ProductController` gère les requêtes HTTP, interagit avec la couche service pour manipuler les données, et retourne les vues Thymeleaf appropriées.

```java
package com.youssef.springweb.web;

@Controller
@AllArgsConstructor
public class ProductController {
    private ProductService productService;

    @GetMapping("/user/products")
    public String index(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("productList", products);
        return "products";
    }

    @GetMapping("/admin/delete")
    public String deleteProduct(@RequestParam(name = "id") Long id) {
        productService.deleteById(id);
        return "redirect:/user/products";
    }

    @PostMapping("/admin/saveProduct")
    public String saveProduct(@Valid Product product, BindingResult result) {
        if (result.hasErrors()) {
            return "new-product";
        }
        productService.addProduct(product);
        return "redirect:/user/products";
    }
    // ... autres méthodes pour la mise à jour
}
```

### Configuration de la Sécurité

Le fichier `SecurityConfig.java` définit les règles d'authentification et d'autorisation. Pour ce TP, une authentification en mémoire (`InMemoryUserDetailsManager`) a été utilisée pour définir des utilisateurs et leurs rôles.

```java
package com.youssef.springweb.security;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                User.withUsername("admin").password(passwordEncoder().encode("123")).roles("ADMIN", "USER").build(),
                User.withUsername("user1").password(passwordEncoder().encode("123")).roles("USER").build(),
                User.withUsername("user2").password(passwordEncoder().encode("123")).roles("USER").build()
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(ar -> ar
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/user/**").hasRole("USER")
                    .anyRequest().authenticated()
                )
                .build();
    }
}
```

### Vues avec Thymeleaf et Bootstrap

Un template de base `layout.html` a été créé pour unifier l'apparence des pages. Il inclut Bootstrap pour le style.

**Fichier `layout.html` :**
```html
<!DOCTYPE html>
<html lang="en"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout">
<head>
    <meta charset="UTF-8">
    <title>Products</title>
    <link rel="stylesheet" type="text/css" th:href="@{/webjars/bootstrap/5.3.8/css/bootstrap.min.css}">
</head>
<body>
    <!-- ... Navbar ... -->
    <div layout:fragment="content">
        <!-- Le contenu de chaque page sera inséré ici -->
    </div>
</body>
</html>
```

---

## 4. Configuration de la Base de Données

Initialement, le projet a été configuré pour utiliser une base de données MySQL. Cependant, pour simplifier le développement et assurer la portabilité, la configuration a été basculée vers **H2**, une base de données en mémoire.

**Fichier `application.properties` :**
```properties
spring.datasource.url=jdbc:h2:mem:product-db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# Activer la console H2 pour le débogage
spring.h2.console.enabled=true

# Stratégie de génération du schéma (update pour ne pas perdre les données à chaque redémarrage)
spring.jpa.hibernate.ddl-auto=update
```
La console H2 est accessible à l'adresse `http://localhost:8080/h2-console` pour inspecter la base de données en cours d'exécution.

---

## 5. Difficultés Rencontrées et Solutions

1.  **Erreur de Connexion à la Base de Données :** Au démarrage, l'application échouait avec une erreur `Communications link failure`. Ce problème était lié à la connexion avec le serveur MySQL local (XAMPP).
    *   **Solution :** Pour contourner ce problème et se concentrer sur la logique applicative, la base de données a été remplacée par **H2**, ce qui a immédiatement résolu le problème de démarrage.

2.  **Problème de Style Bootstrap :** Les styles CSS de Bootstrap ne s'appliquaient pas correctement lors de la navigation vers des URL imbriquées comme `/user/products`. La cause était l'utilisation de chemins relatifs pour les fichiers CSS dans le template Thymeleaf.
    *   **Solution :** Les chemins ont été corrigés en utilisant la syntaxe `@{/...}` de Thymeleaf (`th:href="@{/webjars/...}"`). Cette syntaxe génère des URL absolues par rapport au contexte de l'application, assurant que les ressources sont toujours trouvées.

---

## 6. Conclusion

Ce TP a permis de consolider les compétences sur l'écosystème Spring en construisant une application web fonctionnelle de A à Z. Les points clés abordés sont :

*   La mise en place rapide d'une application web robuste avec **Spring Boot**.
*   La gestion de la persistance des données de manière efficace avec **Spring Data JPA**.
*   La sécurisation des routes web avec **Spring Security**, en gérant l'authentification et les autorisations par rôles.
*   La création de vues dynamiques et modulaires avec **Thymeleaf** et l'intégration d'un framework frontend comme **Bootstrap**.
*   Le débogage de problèmes courants liés à la configuration de la base de données et à la gestion des ressources statiques dans une application web.
