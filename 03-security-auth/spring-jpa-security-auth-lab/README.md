# User Authentication & Role Management: JPA Many-to-Many Architecture

A specialized Spring Boot application demonstrating advanced relational mapping and secure identity management. This project focuses on implementing robust Many-to-Many relationships and secure identifier generation patterns within a Java enterprise context.

## Technical Architecture

The system implements a decoupled service-oriented architecture designed to handle complex authentication schemas:

1.  **Persistence Layer**: Utilizing **Spring Data JPA** and **Hibernate** for bidirectional Many-to-Many mapping.
2.  **Service Layer**: Encapsulating business logic and maintaining transactional integrity using Spring's `@Transactional` orchestration.
3.  **Security Integration**: Implementing best practices for sensitive data handling, including password serialization control and UUID-based identity management.

---

## Technical Stack

*   **Framework**: Spring Boot 3
*   **Persistence**: Spring Data JPA / Hibernate
*   **Database**: H2 In-Memory (Development)
*   **Serialization**: Jackson (handling access control via `@JsonIgnoreProperties`)
*   **Productivity**: Lombok
*   **Build Tool**: Maven

---

## Core Implementations

### 1. Advanced Many-to-Many Mapping
*   **Bidirectional Relationship**: Efficient management of User-Role associations using the `mappedBy` attribute to define relationship ownership.
*   **Automated Junction Tables**: Leveraging Hibernate's auto-generation for optimized association tables.
*   **Eager Fetching**: Strategic use of `FetchType.EAGER` for immediate role loading, preventing `LazyInitializationException` in security-critical contexts.

### 2. Secure Identity Management
*   **UUID Strategy**: Shifting from predictable numerical auto-incrementing IDs to secure, globally unique identifiers (UUIDs) generated at the service level.
*   **Write-Only Passwords**: Utilizing Jackson's `@JsonIgnoreProperties(access = Access.WRITE_ONLY)` to ensure that sensitive credentials are never leaked during data exposition (GET requests).
*   **Unique Constraints**: Enforcement of database-level integrity using `@Column(unique = true)` for sensitive identifiers like usernames.

### 3. Transactional Consistency
*   **Atomic State Synchronization**: Leveraging Spring's Transactional context to ensure that updates to persistent collections (e.g., adding a role to a user) are synchronized automatically without redundant repository calls.

---

## Project Structure

```text
├── src/main/java/com/youssef/manytomany/
│   ├── entities/      # JPA Entity definitions (User, Role)
│   ├── repositories/  # Spring Data JPA Repository interfaces
│   ├── service/       # Transactional Business Logic
│   └── ManyToManyApplication.java # System Entry Point
└── pom.xml            # Dependency management
```

---

## Deployment & Setup

### Prerequisites
*   Java 17 (OpenJDK)
*   Maven 3.8+

### Execution Sequence
1.  **Clone the repository**:
    ```bash
    git clone git@github.com:yss-ef/spring-jpa-security-auth-lab.git
    cd spring-jpa-security-auth-lab
    ```
2.  **Launch the application**:
    ```bash
    mvn spring-boot:run
    ```
3.  **Inspect Data Tier**:
    Access the H2 Console at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`).

---

*Authored by Youssef Fellah.*

*Developed for the Engineering Cycle - Mundiapolis University.*
