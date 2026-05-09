# Application Backend avec RESTController (Spring Boot)

Ce document détaille l'implémentation de l'API REST, développée avec Spring Boot. Elle constitue la partie backend du projet full-stack de gestion de produits.

## 1. Rôle de l'Application

Le backend expose une API REST qui gère la logique métier et la persistance des données pour l'entité `Product`. Il sert de source de vérité pour l'application frontend (Angular) et gère toutes les opérations CRUD.

## 2. Technologies Utilisées

*   **Framework** : Spring Boot
*   **Accès aux données** : Spring Data JPA
*   **API** : Spring Web
*   **Base de données** : H2 (en mémoire)
*   **Utilitaires** : Lombok pour la réduction du code boilerplate.
*   **Build Tool** : Maven

## 3. Détails de l'Implémentation

L'application suit une architecture en couches classique (Contrôleur, Service, Repository).

### Couche de Persistance (JPA)

**Entité `Product.java`**
Représente un produit dans la base de données. Les annotations de validation (`@NotEmpty`, `@Min`) garantissent l'intégrité des données au niveau de l'application.

```java
// src/main/java/com/youssef/backendapp/entities/Product.java
@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;
    @Min(0)
    private double price;
    private Boolean selected;
}
```

**Repository `ProductRepository.java`**
Interface Spring Data JPA pour un accès simplifié à la base de données, fournissant les méthodes CRUD sans implémentation manuelle.

```java
// src/main/java/com/youssef/backendapp/repositories/ProductRepository.java
public interface ProductRepository extends JpaRepository<Product,Long> {
}
```

### Couche Web (API REST)

**Contrôleur `ProductRestAPI.java`**
Expose les endpoints REST pour les opérations sur les produits. L'annotation `@CrossOrigin("*")` est essentielle pour autoriser les requêtes provenant du client Angular.

```java
// src/main/java/com/youssef/backendapp/web/ProductRestAPI.java
@RestController
@CrossOrigin("*")
@RequestMapping("/api")
@AllArgsConstructor
public class ProductRestAPI {
    private ProductService productService;

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return productService.findAll();
    }

    @DeleteMapping("/products/{id}")
    public void deleteProductById(@PathVariable(name = "id") Long id){
        productService.deleteById(id);
    }
}
```

### Initialisation des Données

Au démarrage, un `CommandLineRunner` insère un jeu de données de test dans la base de données H2, ce qui est pratique pour le développement et les démonstrations.

```java
// src/main/java/com/youssef/backendapp/BackendAppApplication.java
@Bean
CommandLineRunner init(ProductService productService) {
    return args -> {
        productService.addProduct(new Product(null, "ordinateur", 5000, true ));
        productService.addProduct(new Product(null, "telephone", 2500, true ));
        // ...
    };
}
```

## 4. Configuration de la Base de Données

Le projet utilise une base de données H2 en mémoire, configurée dans `application.properties`.

```properties
# src/main/resources/application.properties
spring.datasource.url=jdbc:h2:mem:product-db
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=create
```
La console H2 est disponible à l'adresse `http://localhost:8080/h2-console` pour l'inspection de la base de données.

## 5. Pour commencer

1.  **Lancer l'application** :
    *   Via un IDE : exécutez la méthode `main` de la classe `BackendAppApplication`.
    *   Via Maven :
        ```bash
        mvn spring-boot:run
        ```
2.  L'API sera disponible sur `http://localhost:8080`.
