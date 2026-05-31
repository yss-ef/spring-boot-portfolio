# Hospital management system: JPA and Spring Data architecture

Bottom Line Up Front: This project implements a high-performance backend system
utilizing advanced data persistence patterns for healthcare management. It
focuses on complex relational modeling, transactional integrity, and optimized
data access layers using the Spring Boot ecosystem.

## Technical architecture

The application uses a decoupled multi-tier architecture to ensure scalability
and maintainable business logic:

1.  **Web layer**: RESTful API endpoints for external data orchestration.
2.  **Service layer**: Encapsulated business logic utilizing Spring's
    `@Transactional` for atomic operation management.
3.  **Persistence layer**: Highly optimized DAO layer leveraging Spring Data JPA
    and Hibernate ORM.
4.  **Data tier**: Relational modeling with H2 (Development) and support for
    production-grade MySQL/PostgreSQL.

---

## Domain modeling and relationships

The system simulates a real-world clinical workflow through structured entity
relationships:

*   **One-to-many**: Patient to Appointments mapping.
*   **Many-to-one**: Appointments to Medical Practitioners (Doctors).
*   **One-to-one**: Direct linkage between Appointments and clinical
    Consultations.
*   **Bidirectionality**: Advanced use of `mappedBy` to manage relationship
    ownership and prevent foreign key redundancy.

---

## Technical stack

*   **Framework**: Spring Boot 3
*   **Persistence**: Spring Data JPA / Hibernate
*   **Database**: H2 In-Memory (Prototypes) / MySQL (Enterprise)
*   **Serialization**: Jackson (handling infinite recursion via `@JsonIgnore`)
*   **Productivity**: Lombok (boilerplate reduction)
*   **Build Tool**: Maven

---

## Core implementations

### 1. Advanced JPA mapping
*   **Enum persistence**: Usage of `@Enumerated(EnumType.STRING)` to ensure
    database readability and decoupling from ordinal indices.
*   **Validation**: Integration of Bean Validation for entity-level data
    integrity.
*   **Lazy loading**: Strategic fetching strategies to optimize memory footprint
    during complex object graph retrievals.

### 2. Service-oriented logic
*   **Atomic transactions**: Ensuring that multi-step operations (for example,
    creating an appointment and initializing a consultation record) succeed or
    fail as a single unit.
*   **Constructor injection**: Adhering to Spring's best practices using
    `@RequiredArgsConstructor` for robust dependency management.

---

## Project structure

```text
├── src/main/java/ma/youssef/exemplepatient/
│   ├── entities/      # JPA Entity definitions and relationships
│   ├── repositories/  # Data access interfaces (JpaRepository)
│   ├── service/       # Business logic and transaction management
│   └── web/           # REST Controller implementations
└── pom.xml            # System dependency orchestration
```

---

## Deployment and setup

### Prerequisites
*   Java 17 (OpenJDK)
*   Maven 3.8+

### Launch sequence
1.  **Initialize**:
    ```bash
    git clone git@github.com:yss-ef/spring-jpa-hospital-management.git
    cd spring-jpa-hospital-management
    ```
2.  **Compile and execute**:
    ```bash
    mvn spring-boot:run
    ```
3.  **H2 console access**:
    Navigate to `http://localhost:8080/h2-console` (JDBC URL:
    `jdbc:h2:mem:testdb`).

Authored by Youssef Fellah.
Developed for the Engineering Cycle - Mundiapolis University.
