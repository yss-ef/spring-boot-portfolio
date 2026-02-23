# 🏥 Hospital Management System | JPA & Spring Data Architecture

> **Advanced JEE Architecture Practice**
> This Spring Boot application serves as a practical implementation of modern **Data Persistence** concepts. It focuses on modeling complex relationships (One-to-Many, Many-to-One, One-to-One) and utilizing **Spring Data JPA** to streamline the DAO layer, following the architectural patterns of an enterprise-grade hospital management system.

## 📑 Table of Contents

* [Technologies & Dependencies](https://www.google.com/search?q=%23-technologies--dependencies)
* [System Architecture](https://www.google.com/search?q=%23-system-architecture)
* [Core Concepts & Annotations](https://www.google.com/search?q=%23-core-concepts--annotations)
* [Source Code Analysis](https://www.google.com/search?q=%23-source-code-analysis)
* [Local Deployment](https://www.google.com/search?q=%23-local-deployment)

## 🛠 Technologies & Dependencies

This project leverages the **Spring Boot** ecosystem for rapid configuration and optimized dependency management:

| Technology | Role |
| --- | --- |
| **Spring Data JPA** | Facilitates data access layer implementation and reduces boilerplate code. |
| **H2 Database** | Embedded in-memory database, perfect for rapid development and testing. |
| **Lombok** | Reduces code verbosity by automating getters, setters, and constructors. |
| **Spring Web** | Used to build RESTful APIs for data exposition. |
| **Hibernate (ORM)** | Object-Relational Mapping layer for managing entities and relationships. |

## 🏗 System Architecture

### Entity Modeling

The application is structured around four core business domains, simulating a real-world hospital workflow:

* **Patient**: Manages healthcare user information.
* **Doctor (Medecin)**: Professional profiles of healthcare practitioners.
* **Appointment (RendezVous)**: Orchestrates care sessions.
* **Consultation**: Records diagnostics and results.

### Software Design

The project follows a strict multi-tier architecture:

1. **Web Layer**: REST Controllers handling HTTP requests.
2. **Service Layer** (`@Service`): Encapsulates business logic and ensures **transactional integrity** (`@Transactional`).
3. **Repository Layer**: Interfaces inheriting from `JpaRepository` for seamless CRUD operations.

## 🔍 Core Concepts & Annotations

### Relationship Management

* **Bidirectionality**: The use of `mappedBy` on the non-owning side of the relationship (e.g., `Patient`) prevents duplicate foreign keys.
* **JSON Serialization**: Utilization of `@JsonIgnore` to break infinite serialization loops (`Patient -> Appointment -> Patient`) when exposing REST APIs.

### Essential Annotations

* **`@Entity`**: Marks a class as a JPA entity mapped to a database table.
* **`@Enumerated(EnumType.STRING)`**: Persists the Enum name as a string rather than its numerical index.
* **`@RequiredArgsConstructor`**: Generates constructor-based dependency injection (Spring's recommended pattern).

## 💻 Source Code Analysis

### 1. Entity and Relationships: `RendezVous.java`

The following snippet illustrates Enum management and multiple relationships.

```java
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class RendezVous {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    
    // Persists the Enum as a String in the database
    @Enumerated(EnumType.STRING)
    private StatutRendezVous statut;

    // Many-to-One: Multiple appointments for one patient
    @ManyToOne
    private Patient patient; 

    // One-to-One: One appointment leads to exactly one consultation
    @OneToOne(mappedBy = "rendezVous")
    @JsonIgnore
    private Consultation consultation; 
}

```

### 2. Transactional Service Layer: `HopitalServiceImpl.java`

Ensures atomicity for business operations.

```java
@Service
@Transactional
@RequiredArgsConstructor
public class HopitalServiceImpl implements IHopitalService {
    private final PatientRepository patientRepository;
    
    @Override
    public Patient savePatient(Patient patient) {
        // Business logic and validation occurs here
        return patientRepository.save(patient);
    }
}

```

## 🚀 Local Deployment

To configure and launch this project on your **Fedora 43** environment:

**1. Install prerequisites:**

```bash
sudo dnf install java-17-openjdk-devel maven

```

**2. Compile and Run:**

```bash
# Navigate to the repository root
mvn spring-boot:run

```

**3. Access H2 Console:**
Once the application is running, you can inspect the in-memory database at:
`http://localhost:8080/h2-console`
*(JDBC URL: `jdbc:h2:mem:testdb`)*

---

*Authored by Youssef Fellah.*

*Developed as part of the 2nd year Engineering Cycle - Mundiapolis University.*
