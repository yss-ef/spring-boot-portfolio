# Spring Data JPA: advanced persistence and ORM

Bottom Line Up Front: This project provides a technical deep-dive into the Spring
Data JPA ecosystem, focusing on Object-Relational Mapping (ORM), JPQL query
optimization, and relational database lifecycle management. It demonstrates
the implementation of a robust persistence layer, transitioning from in-memory
prototyping to production-grade MySQL environments.

## Technical architecture

The application implements a streamlined data access architecture designed for
high-performance retrieval and automated schema management:

1.  **Persistence layer**: Utilizing Spring Data JPA and Hibernate to bridge
    the gap between Java object models and relational database schemas.
2.  **Repository layer**: Implementing the Repository Pattern through
    `JpaRepository` interfaces, enabling zero-boilerplate CRUD operations.
3.  **Service layer**: Exposing the persistence logic through RESTful endpoints
    using Spring Web.
4.  **Database integration**: Dynamic orchestration of database connectivity
    via `application.properties` for both H2 and MySQL.

---

## Technical stack

*   **Framework**: Spring Boot 3
*   **Persistence**: Spring Data JPA / Hibernate (ORM)
*   **Database**: H2 In-Memory (Prototyping) / MySQL (Production)
*   **Query Language**: JPQL (Java Persistence Query Language) / Method Name
    Derivation
*   **Build Tool**: Maven
*   **Productivity**: Lombok

---

## Core implementations

### 1. Advanced query orchestration
*   **Method name derivation**: Leveraging Spring Data's parser to generate
    complex SQL queries from method signatures (for example,
    `findByNameContains`).
*   **JPQL integration**: Utilizing the `@Query` annotation for custom
    object-oriented queries, allowing for precise data extraction without
    depending on native SQL syntax.
*   **Named parameters**: Secure parameter binding using `@Param` to prevent
    SQL injection vulnerabilities.

### 2. Automated schema management
*   **DDL-auto orchestration**: Using Hibernate's lifecycle management
    (`update`, `create-drop`) to automate table creation and schema
    synchronization based on Java `@Entity` definitions.
*   **Identity management**: Implementing `GenerationType.IDENTITY` to delegate
    primary key generation to the underlying database's auto-increment engine.
*   **Relational mapping**: Optimized mapping of Java types (for example,
    `Long` vs `long`) to handle nullability and database constraints
    effectively.

### 3. RESTful data exposition
*   **JSON serialization**: Automated conversion of JPA entities to JSON format
    via Jackson.
*   **Stateless controllers**: Implementation of `@RestController` to provide
    high-performance data streams to client applications.

---

## Project structure

```text
├── src/main/java/ma/youssef/springdata/
│   ├── entities/      # JPA Entity definitions
│   ├── repositories/  # Spring Data Repository interfaces
│   └── web/           # RESTful API Controllers
├── src/main/resources/
│   └── application.properties # Persistence & Database configuration
└── pom.xml            # System dependency management
```

---

## Deployment and setup

### Prerequisites
*   Java 17 (OpenJDK)
*   Maven 3.8+
*   MySQL Server (for production profiles)

### Launch sequence
1.  **Database setup**:
    ```sql
    CREATE DATABASE eb_db;
    ```
2.  **Environment configuration**:
    Update `application.properties` with your database credentials.
3.  **Execution**:
    ```bash
    mvn spring-boot:run
    ```

Authored by Youssef Fellah.
Developed for the Engineering Cycle - Mundiapolis University.
