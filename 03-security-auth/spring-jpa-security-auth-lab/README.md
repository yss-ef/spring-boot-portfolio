# User authentication and role management: JPA many-to-many architecture

Bottom Line Up Front: This project provides a specialized Spring Boot
application demonstrating advanced relational mapping and secure identity
management. It focuses on implementing robust many-to-many relationships and
secure identifier generation patterns within a Java enterprise context.

## Technical architecture

The system implements a decoupled service-oriented architecture designed to
handle complex authentication schemas:

1.  **Persistence layer**: Utilizing Spring Data JPA and Hibernate for
    bidirectional many-to-many mapping.
2.  **Service layer**: Encapsulating business logic and maintaining
    transactional integrity using Spring's `@Transactional` orchestration.
3.  **Security integration**: Implementing best practices for sensitive data
    handling, including password serialization control and UUID-based identity
    management.

---

## Technical stack

*   **Framework**: Spring Boot 3
*   **Persistence**: Spring Data JPA / Hibernate
*   **Database**: H2 In-Memory (Development)
*   **Serialization**: Jackson (handling access control via
    `@JsonIgnoreProperties`)
*   **Productivity**: Lombok
*   **Build Tool**: Maven

---

## Core implementations

### 1. Advanced many-to-many mapping
*   **Bidirectional relationship**: Efficient management of User-Role
    associations using the `mappedBy` attribute to define relationship
    ownership.
*   **Automated junction tables**: Leveraging Hibernate's auto-generation for
    optimized association tables.
*   **Eager fetching**: Strategic use of `FetchType.EAGER` for immediate role
    loading, preventing `LazyInitializationException` in security-critical
    contexts.

### 2. Secure identity management
*   **UUID strategy**: Shifting from predictable numerical auto-incrementing
    IDs to secure, globally unique identifiers (UUIDs) generated at the service
    level.
*   **Write-only passwords**: Utilizing Jackson's
    `@JsonIgnoreProperties(access = Access.WRITE_ONLY)` to ensure that sensitive
    credentials are never leaked during data exposition (GET requests).
*   **Unique constraints**: Enforcement of database-level integrity using
    `@Column(unique = true)` for sensitive identifiers like usernames.

### 3. Transactional consistency
*   **Atomic state synchronization**: Leveraging Spring's transactional context
    to ensure that updates to persistent collections (for example, adding a role
    to a user) are synchronized automatically without redundant repository
    calls.

---

## Project structure

```text
├── src/main/java/com/youssef/manytomany/
│   ├── entities/      # JPA Entity definitions (User, Role)
│   ├── repositories/  # Spring Data JPA Repository interfaces
│   ├── service/       # Transactional Business Logic
│   └── ManyToManyApplication.java # System Entry Point
└── pom.xml            # Dependency management
```

---

## Deployment and setup

### Prerequisites
*   Java 17 (OpenJDK)
*   Maven 3.8+

### Execution sequence
1.  **Clone the repository**:
    ```bash
    git clone git@github.com:yss-ef/spring-jpa-security-auth-lab.git
    cd spring-jpa-security-auth-lab
    ```
2.  **Launch the application**:
    ```bash
    mvn spring-boot:run
    ```
3.  **Inspect data tier**:
    Access the H2 Console at `http://localhost:8080/h2-console` (JDBC URL:
    `jdbc:h2:mem:testdb`).

Authored by Youssef Fellah.
Developed for the Engineering Cycle - Mundiapolis University.
