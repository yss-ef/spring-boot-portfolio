# Hospital Management System: JPA & Spring Data Architecture

A high-performance backend system implementing advanced data persistence patterns for healthcare management. This project focuses on complex relational modeling, transactional integrity, and optimized data access layers using the Spring Boot ecosystem.

## Technical Architecture

The application is engineered using a decoupled multi-tier architecture to ensure scalability and maintainable business logic:

1.  **Web Layer**: RESTful API endpoints for external data orchestration.
2.  **Service Layer**: Encapsulated business logic utilizing Spring's `@Transactional` for atomic operation management.
3.  **Persistence Layer**: Highly optimized DAO layer leveraging Spring Data JPA and Hibernate ORM.
4.  **Data Tier**: Relational modeling with H2 (Development) and support for production-grade MySQL/PostgreSQL.

---

## Domain Modeling & Relationships

The system simulates a real-world clinical workflow through structured entity relationships:

*   **One-to-Many**: Patient to Appointments mapping.
*   **Many-to-One**: Appointments to Medical Practitioners (Doctors).
*   **One-to-One**: Direct linkage between Appointments and clinical Consultations.
*   **Bidirectionality**: Advanced use of `mappedBy` to manage relationship ownership and prevent foreign key redundancy.

---

## Technical Stack

*   **Framework**: Spring Boot 3
*   **Persistence**: Spring Data JPA / Hibernate
*   **Database**: H2 In-Memory (Prototypes) / MySQL (Enterprise)
*   **Serialization**: Jackson (handling infinite recursion via `@JsonIgnore`)
*   **Productivity**: Lombok (boilerplate reduction)
*   **Build Tool**: Maven

---

## Core Implementations

### 1. Advanced JPA Mapping
*   **Enum Persistence**: Usage of `@Enumerated(EnumType.STRING)` to ensure database readability and decoupling from ordinal indices.
*   **Validation**: Integration of Bean Validation for entity-level data integrity.
*   **Lazy Loading**: Strategic fetching strategies to optimize memory footprint during complex object graph retrievals.

### 2. Service-Oriented Logic
*   **Atomic Transactions**: Ensuring that multi-step operations (e.g., creating an appointment and initializing a consultation record) succeed or fail as a single unit.
*   **Constructor Injection**: Adhering to Spring's best practices using `@RequiredArgsConstructor` for robust dependency management.

---

## Project Structure

```text
├── src/main/java/ma/youssef/exemplepatient/
│   ├── entities/      # JPA Entity definitions and relationships
│   ├── repositories/  # Data access interfaces (JpaRepository)
│   ├── service/       # Business logic and transaction management
│   └── web/           # REST Controller implementations
└── pom.xml            # System dependency orchestration
```

---

## Deployment & Setup

### Prerequisites
*   Java 17 (OpenJDK)
*   Maven 3.8+

### Launch Sequence
1.  **Initialize**:
    ```bash
    git clone git@github.com:yss-ef/spring-jpa-hospital-management.git
    cd spring-jpa-hospital-management
    ```
2.  **Compile & Execute**:
    ```bash
    mvn spring-boot:run
    ```
3.  **H2 Console Access**:
    Navigate to `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`).

---

*Authored by Youssef Fellah.*

*Developed for the Engineering Cycle - Mundiapolis University.*
