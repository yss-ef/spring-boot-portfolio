# 🍃 Spring Boot & JPA: Data Persistence (ORM & Hibernate)

> **Academic Practical Work (TP) Report**
> This project demonstrates the implementation of the persistence layer in an enterprise Java application. It illustrates the transition from an in-memory database (H2) to a relational DBMS (MySQL) by leveraging the power of Object-Relational Mapping (ORM) via **JPA**, **Hibernate**, and **Spring Data**.

## 📑 Table of Contents

* [Project Objectives](https://www.google.com/search?q=%23-project-objectives)
* [Architecture & Components](https://www.google.com/search?q=%23%EF%B8%8F-architecture--components)
* [Source Code Analysis](https://www.google.com/search?q=%23-source-code-analysis)
* [Local Deployment](https://www.google.com/search?q=%23-local-deployment)
* [Conclusion & Takeaways](https://www.google.com/search?q=%23-conclusion--takeaways)

## 🎯 Project Objectives

The goal of this lab is to design a RESTful API for product management while automating SQL queries as much as possible. The key steps include:

1. Initialization via Spring Initializr.
2. Defining database entities using Java classes (JPA).
3. Configuring the persistence unit (`application.properties`).
4. Creating repository interfaces to automate CRUD operations.
5. Exposing data via a REST controller.

## 🏗️ Architecture & Components

The application is built on the MVC/Multi-tier model adapted for Spring microservices:

* **Entity (`Product`)**: The exact mirror of the `Product` table in the database.
* **Repository (`ProductRepository`)**: The interface that acts as a direct bridge to the database, translating Java method calls into optimized SQL queries.
* **Controller (`ProductRestService`)**: The network entry point that captures HTTP requests, interacts with the repository, and returns data in JSON format.

## 🔍 Source Code Analysis

### 1. The Data Model Entity (Product.java)

This class defines the structure of our table.

```java
@Entity 
@Data @NoArgsConstructor @AllArgsConstructor
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; 
    private String name;
    private double price;
    private int quantity;
}

```

* **How it works:** The `@Entity` annotation tells Hibernate that this class should be mapped to a database table. `@Id` designates the primary key, and `@GeneratedValue(strategy = GenerationType.IDENTITY)` delegates the ID generation to MySQL's auto-increment feature. The Lombok annotations (`@Data`, etc.) dynamically inject getters, setters, and constructors at compile time to keep the code clean. The `Long` object type is preferred over the primitive `long` because it accepts a `null` value, which is essential before the entity is first persisted in the database.

### 2. The Data Access Interface (ProductRepository.java)

This is where the magic of Spring Data happens.

```java
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContains(String name);
    
    @Query("select p from Product p where p.name like :x")
    List<Product> search(@Param("x") String name);
}

```

* **How it works:** By extending `JpaRepository`, we instantly inherit methods like `save()`, `findAll()`, or `deleteById()` without writing a single line of implementation code.
* For specific needs, Spring Data parses the method name (`findByNameContains`) and automatically generates the corresponding SQL query. For more complex logic, the `@Query` annotation allows us to write **JPQL** (Java Persistence Query Language), which queries the Java objects themselves rather than the underlying SQL tables.

### 3. The Web Service (ProductRestService.java)

Exposing the data to the outside world.

```java
@RestController
public class ProductRestService {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public List<Product> productList() {
        return productRepository.findAll();
    }
}

```

* **How it works:** `@RestController` is a combination of `@Controller` and `@ResponseBody`, meaning that every method directly returns data (usually as JSON) rather than rendering an HTML view. The `@Autowired` annotation handles Dependency Injection: Spring automatically provides a ready-to-use instance of the `ProductRepository` when the controller is instantiated.

## 🚀 Local Deployment

To configure and run this project on a **Fedora 43** environment:

**1. Install system prerequisites:**
Ensure your local environment has the necessary tools installed.

```bash
sudo dnf install java-17-openjdk-devel maven mysql-server
sudo systemctl enable --now mysqld

```

**2. Database Preparation:**
Log into MySQL (`mysql -u root -p`) and create the database defined in your `application.properties`:

```sql
CREATE DATABASE eb_db;

```

**3. Launch the Application:**
From the root of the project repository, execute:

```bash
mvn spring-boot:run

```

The API will be instantly accessible at `http://localhost:8080/products`.

## 💡 Conclusion & Takeaways

One of the most powerful aspects of Spring Data JPA is the invisible orchestration of the database lifecycle.

The `application.properties` file centralizes the configuration (e.g., MySQL credentials). Upon startup, Hibernate reads the `@Entity` annotated classes and executes the DDL (Data Definition Language) to structure the database automatically. The `ProductRepository` interface eliminates all the repetitive boilerplate code associated with JDBC connections and `ResultSet` mapping, making the development of REST APIs remarkably fluid, robust, and secure.

---

*Authored by Youssef Fellah.*

*Developed as part of the 2nd year Engineering Cycle - Mundiapolis University.*
