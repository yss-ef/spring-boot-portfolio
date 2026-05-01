# Spring Data JPA: Advanced Persistence & ORM

A technical deep-dive into the Spring Data JPA ecosystem, focusing on Object-Relational Mapping (ORM), JPQL query optimization, and the management of relational database lifecycles. This project demonstrates the implementation of a robust persistence layer, transitioning from in-memory prototyping to production-grade MySQL environments.

## Technical Architecture

The application implements a streamlined data access architecture designed for high-performance retrieval and automated schema management:

1.  **Persistence Layer**: Utilizing **Spring Data JPA** and **Hibernate** to bridge the gap between Java object models and relational database schemas.
2.  **Repository Layer**: Implementing the Repository Pattern through `JpaRepository` interfaces, enabling zero-boilerplate CRUD operations.
3.  **Service Layer**: Exposing the persistence logic through RESTful endpoints using **Spring Web**.
4.  **Database Integration**: Dynamic orchestration of database connectivity via `application.properties` for both H2 and MySQL.

---

## Technical Stack

*   **Framework**: Spring Boot 3
*   **Persistence**: Spring Data JPA / Hibernate (ORM)
*   **Database**: H2 In-Memory (Prototyping) / MySQL (Production)
*   **Query Language**: JPQL (Java Persistence Query Language) / Method Name Derivation
*   **Build Tool**: Maven
*   **Productivity**: Lombok

---

## Core Implementations

### 1. Advanced Query Orchestration
*   **Method Name Derivation**: Leveraging Spring Data's parser to generate complex SQL queries from method signatures (e.g., `findByNameContains`).
*   **JPQL Integration**: Utilizing the `@Query` annotation for custom object-oriented queries, allowing for precise data extraction without depending on native SQL syntax.
*   **Named Parameters**: Secure parameter binding using `@Param` to prevent SQL injection vulnerabilities.

### 2. Automated Schema Management
*   **DDL-Auto Orchestration**: Using Hibernate's lifecycle management (`update`, `create-drop`) to automate table creation and schema synchronization based on Java `@Entity` definitions.
*   **Identity Management**: Implementing `GenerationType.IDENTITY` to delegate primary key generation to the underlying database's auto-increment engine.
*   **Relational Mapping**: Optimized mapping of Java types (e.g., `Long` vs `long`) to handle nullability and database constraints effectively.

### 3. RESTful Data Exposition
*   **JSON Serialization**: Automated conversion of JPA entities to JSON format via Jackson.
*   **Stateless Controllers**: Implementation of `@RestController` to provide high-performance data streams to client applications.

---

## Project Structure

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

## Deployment & Setup

### Prerequisites
*   Java 17 (OpenJDK)
*   Maven 3.8+
*   MySQL Server (for production profiles)

### Launch Sequence
1.  **Database Setup**:
    ```sql
    CREATE DATABASE eb_db;
    ```
2.  **Environment Configuration**:
    Update `application.properties` with your database credentials.
3.  **Execution**:
    ```bash
    mvn spring-boot:run
    ```

---

*Authored by Youssef Fellah.*

*Developed for the Engineering Cycle - Mundiapolis University.*
