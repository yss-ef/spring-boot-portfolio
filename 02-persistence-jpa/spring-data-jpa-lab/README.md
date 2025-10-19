# Compte Rendu du TP : ORM, JPA, Hibernate et Spring Data

Ce document présente le rapport du travail pratique sur l'implémentation de la **persistance des données en Java** en utilisant les technologies **ORM (Object-Relational Mapping)**, **JPA (Java Persistence API)**, **Hibernate** comme implémentation de JPA, et **Spring Data JPA** pour simplifier l'accès aux données. L'objectif principal est de développer une application Spring Boot capable de gérer des produits, en respectant les principes de la persistance objet-relationnel.

---

## 1. Énoncé du TP

La ressource principale pour ce TP est la vidéo suivante : [https://www.youtube.com/watch?v=cz3p4y7tUEs](https://www.youtube.com/watch?v=cz3p4y7tUEs)

Les étapes suivies pour la réalisation de ce TP sont les suivantes :

* **Installation d'IntelliJ Ultimate** : Préparation de l'environnement de développement.
* **Création du projet Spring Initializr** : Initialisation d'un projet Spring Boot avec les dépendances nécessaires.
* **Création de l'entité JPA Product** : Définition de la structure des données à persister.
* **Configuration de l'unité de persistance** : Paramétrage de la base de données dans `application.properties`.
* **Création de l'interface JPA Repository** : Utilisation de Spring Data JPA pour les opérations d'accès aux données.
* **Test des opérations de gestion de produits** : Ajout, consultation, recherche, mise à jour et suppression de produits.
* **Migration de H2 Database vers MySQL** : Adaptation de la configuration pour une base de données relationnelle externe.
* **Création d'un service REST** : Exposition des fonctionnalités de gestion de produits via une API RESTful.

---

## 2. Conception de l'Application

L'application est structurée autour des composants clés de Spring Boot et JPA :

* **Entité (Product)** : Représente la table `Product` dans la base de données.
* **Repository (ProductRepository)** : Fournit des méthodes pour interagir avec la base de données pour l'entité `Product`. Spring Data JPA réduit considérablement le code boilerplate nécessaire.
* **Application principale (OrmJpaHibernateSpringDataApplication)** : Contient la logique de démarrage de l'application et les tests d'opérations sur les produits (via `CommandLineRunner`).
* **Contrôleur REST (ProductRestService)** : Expose des endpoints HTTP pour accéder aux données des produits.

---

## 3. Implémentation et Code Source

### Étape 2 : Création du projet Spring Initializr

Le projet a été créé avec Spring Initializr en incluant les dépendances suivantes :

* Spring Data JPA
* H2 Database
* Spring Web
* Lombok
* MySQL Driver

### Étape 3 : Création de l'entité JPA Product

L'entité `Product` représente les produits à stocker en base de données. Les annotations `@Entity`, `@Id`, `@GeneratedValue` sont utilisées pour mapper la classe à une table et définir sa clé primaire. Les annotations Lombok (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`) sont utilisées pour générer automatiquement les méthodes courantes.

Fichier `Product.java` :

```java
package ma.youssef.orm_jpa_hibernate_springdata.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Pour une entité
@Data @NoArgsConstructor @AllArgsConstructor
/*
    @Data : Pour generer les constructeurs.
    @NoArgsConstructor et @AllArgsConstructor : Pour les constructeurs de la classe.
*/
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // Pour generer la cle primaire de la table Produit
    private Long id; //Le type doit etre Long et non long car ce dernier n'est pas nullable.
    private String name;
    private double price;
    private int quantity;
}
```
### Étape 4 : Configuration de l'unité de persistance dans application.properties

Le fichier `application.properties` permet de configurer la base de données. Initialement, H2 a été utilisé, puis migré vers MySQL.

Exemple de configuration H2 (initiale) :

```Properties
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
```

Exemple de configuration MySQL (après migration) :

```Properties
spring.datasource.url=jdbc:mysql://localhost:3306/eb_db
spring.datasource.username=root
spring.datasource.password=spring
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
### Étape 5 : Création de l'interface JPA Repository

L'interface `ProductRepository` étend `JpaRepository`, ce qui permet d'hériter de nombreuses méthodes CRUD (Create, Read, Update, Delete) sans écrire de code. Des méthodes de recherche personnalisées sont également définies, certaines suivant les conventions de nommage de Spring Data JPA et d'autres utilisant l'annotation `@Query` pour des requêtes JPQL spécifiques.

Fichier `ProductRepository.java` :

```java
package ma.youssef.orm_jpa_hibernate_springdata.Repository;

import ma.youssef.orm_jpa_hibernate_springdata.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContains(String name);
    List<Product> findByPriceGreaterThan(double price);

    @Query("select p from Product p where p.name like :x")
    List<Product> search(@Param("x") String name);

    @Query("select p from Product p where p.price > :x")
    List<Product> searchByPrice(@Param("x") double price);
}
```

### Étape 8 : Création d'un service REST

Un contrôleur REST (`ProductRestService`) est implémenté pour exposer les données des produits via des endpoints HTTP.

Fichier `ProductRestService.java` :

```java
package ma.youssef.orm_jpa_hibernate_springdata.Web;

import ma.youssef.orm_jpa_hibernate_springdata.Entities.Product;
import ma.youssef.orm_jpa_hibernate_springdata.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class ProductRestService {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public List<Product> productList() {
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Product productListId(@PathVariable Integer id) {
        return productRepository.findById(Long.valueOf(id)).get();
    }
}
```

* `GET /products` : Retourne la liste de tous les produits.
* `GET /products/{id}` : Retourne un produit spécifique en fonction de son ID.

---

## 4. Conclusion

Ce travail pratique a permis de mettre en œuvre les concepts fondamentaux de la **persistance des données avec JPA, Hibernate et Spring Data JPA** dans une application Spring Boot. Nous avons pu créer une entité, définir une interface de repository pour gérer les opérations CRUD et les requêtes personnalisées, et exposer ces fonctionnalités via une API RESTful. La flexibilité de Spring Data JPA, combinée à la puissance de l'ORM, simplifie grandement le développement de la couche de persistance et la gestion des données dans les applications modernes.

---

## 5. Notes Personnelles

L'un des aspects les plus puissants de Spring Data JPA est la manière dont le contrôle de la base de données est orchestré.

Le point de départ est le fichier `application.properties`, où l'on déclare la base de données à utiliser (dans notre cas, MySQL). C'est la seule configuration nécessaire pour la connexion. Ensuite, la simple création d'une classe Java annotée avec `@Entity` suffit à Spring pour générer automatiquement la table correspondante dans la base de données au démarrage de l'application.

Enfin, toute la logique de manipulation de cette table (CRUD, recherches, etc.) est centralisée dans l'interface `ProductRepository`. En définissant simplement des signatures de méthodes, Spring Data JPA génère les implémentations nécessaires. Ces méthodes sont ensuite appelées soit depuis la classe principale pour des tests, soit depuis un contrôleur web pour exposer les données, rendant le processus de développement remarquablement fluide et efficace.


