# 🔐 Authorization Management | JPA Architecture: Many-To-Many & Inheritance

> **Advanced JEE Architecture Practice**
> This Spring Boot application demonstrates the implementation of complex **Many-To-Many** relationships and **JPA Inheritance** strategies. It models a robust User and Role management system, emphasizing data security, unique identifier generation, and transactional integrity.

## 📑 Table of Contents

* [Core Concepts](https://www.google.com/search?q=%23-core-concepts)
* [Annotations & Security](https://www.google.com/search?q=%23-annotations--security)
* [Source Code Analysis](https://www.google.com/search?q=%23-source-code-analysis)
* [Data Architecture](https://www.google.com/search?q=%23-data-architecture)
* [Local Deployment](https://www.google.com/search?q=%23-local-deployment)

## 🏗️ Core Concepts

### 1. Many-To-Many Relationship (`User` ↔ `Role`)

The project models a bidirectional relationship where a user can possess multiple roles, and each role can be assigned to multiple users.

* **Junction Table**: Hibernate automatically generates an association table (defaulting to `users_roles`) to store the foreign key mappings.
* **Bidirectionality**: The `mappedBy` attribute is used on the `Role` entity to define `User` as the owner of the relationship.

### 2. Identifier Management (UUID)

Instead of standard numerical auto-incrementing IDs, user identifiers are dynamically generated as **UUIDs** (`String`) within the service layer. This approach enhances security and facilitates the merging of distributed databases.

### 3. Transactional Integrity

Utilizing `@Transactional` in the Service layer ensures that any modification to a persistent object (e.g., `user.getRoles().add(role)`) is automatically synchronized with the database during the *commit* phase, without requiring a manual call to `repository.save()`.

## 🛡️ Annotations & Security

| Annotation | Technical Role |
| --- | --- |
| **`@ManyToMany`** | Defines the many-to-many relationship between entities. |
| **`@Column(unique = true)`** | Enforces field uniqueness (e.g., `username`) at the database level. |
| **`@ToString.Exclude`** | (Lombok) Prevents infinite logging loops when calling `toString()`. |
| **`@JsonIgnoreProperties`** | (Jackson) Hides sensitive data (e.g., `password`) during JSON serialization (GET). |
| **`FetchType.EAGER`** | Immediately loads associated collections to prevent `LazyInitializationException`. |

## 💻 Source Code Analysis

### 1. Secured Entity: `User.java`

Leveraging Jackson to protect passwords and Lombok for code clarity.

```java
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    private String userId; // Generated via UUID.randomUUID()
    
    @Column(unique = true, length = 20)
    private String username;
    
    // Write-only access ensures the password is never leaked in API responses
    @JsonIgnoreProperties(access = JsonIgnoreProperties.Access.WRITE_ONLY)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();
}

```

### 2. Service Layer: `UserServiceImpl.java`

Handling business logic and the generation of secure identifiers.

```java
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    
    @Override
    public User addNewUser(User user) {
        // Generation of a unique secure ID
        user.setUserId(UUID.randomUUID().toString());
        // Note: Password hashing (BCrypt) is recommended here
        return userRepository.save(user);
    }
}

```

## 🚀 Local Deployment

To configure and launch this project on your **Fedora 43** environment:

**1. Install Prerequisites:**

```bash
sudo dnf install java-17-openjdk-devel maven

```

**2. Compile and Run:**

```bash
# From the project root
mvn spring-boot:run

```

**3. Database Visualization (H2 Console):**
Access the web console at: `http://localhost:8080/h2-console`
*(Use JDBC URL: `jdbc:h2:mem:testdb`)*

---

*Authored by Youssef Fellah.*

*Developed as part of the 2nd year Engineering Cycle - Mundiapolis University.*
