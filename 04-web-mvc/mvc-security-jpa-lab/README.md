# Spring MVC security and JPA laboratory

Bottom Line Up Front: This project provides a comprehensive exploration of the
Spring Boot ecosystem, focused on building a secure, multi-tier web application
for product lifecycle management. It serves as a technical deep-dive into
Spring Security's authorization engine and Spring Data JPA's persistence layer.

## Project architecture

The application implements a classic MVC (Model-View-Controller) architecture
with a clear separation of concerns:

1.  **View tier**: Server-side rendering using Thymeleaf and Bootstrap 5 for
    dynamic, responsive interfaces.
2.  **Controller tier**: Spring MVC REST-style controllers handling request
    mapping and data flow.
3.  **Service/persistence tier**: Spring Data JPA managing relational data
    mapping to an H2 in-memory database.
4.  **Security tier**: Spring Security filter chain managing session-based
    authentication and role-based access.

---

## Technical stack

*   **Framework**: Spring Boot 3
*   **Security**: Spring Security (Role-Based Access Control)
*   **Persistence**: Spring Data JPA / Hibernate
*   **Database**: H2 In-Memory (Development) / MySQL (Production Ready)
*   **Templating**: Thymeleaf / Thymeleaf Layout Dialect
*   **Frontend**: Bootstrap 5
*   **Build Tool**: Maven

---

## Interface showcase

| Login Portal | Product Management |
| --- | --- |
| <img src="screenshots/login.png" width="400" alt="Login Page"> | <img src="screenshots/products.png" width="400" alt="Products Screen"> |

| Creation Workflow | Modification Workflow |
| --- | --- |
| <img src="screenshots/new.png" width="400" alt="Creation Page"> | <img src="screenshots/update.png" width="400" alt="Update Page"> |

---

## Core implementations

### 1. Robust security configuration
*   **Stateless vs. session**: Implements secure session management for
    server-side rendered templates.
*   **RBAC (Role-based access control)**:
    *   `USER`: Authorized for read-only catalog access.
    *   `ADMIN`: Full administrative privileges for CRUD operations.
*   **Endpoint protection**: Granular request matching using `HttpSecurity`
    configurations.

### 2. Relational data mapping (JPA)
*   **Bean validation**: Integration of JSR-303/JSR-380 (`@NotEmpty`, `@Size`,
    `@Min`) at the entity level to ensure data integrity.
*   **Pagination and sorting**: High-performance catalog browsing implemented
    via Spring Data's `PagingAndSortingRepository`.
*   **Automated schema generation**: Leveraging Hibernate's DDL-auto features
    for rapid prototyping.

### 3. Dynamic UI rendering
*   **Thymeleaf fragments**: Modular UI design using layout dialects for
    consistent header and footer management.
*   **Reactive templates**: Dynamic path resolution using Thymeleaf's `@`
    syntax to prevent broken asset links across nested routes.

---

## Project structure

```text
├── src/main/java/com/youssef/springweb/
│   ├── entities/      # JPA Entity definitions
│   ├── repositories/  # Spring Data JPA interfaces
│   ├── security/      # Spring Security configurations
│   └── web/           # Spring MVC Controllers
├── src/main/resources/
│   ├── templates/     # Thymeleaf HTML views
│   └── application.properties # System configuration
└── pom.xml            # Dependency management
```

---

## Deployment and setup

### Prerequisites
*   Java 17 (OpenJDK)
*   Maven 3.8+

### Launch sequence
1.  **Clone the repository**:
    ```bash
    git clone git@github.com:yss-ef/mvc-security-jpa-lab.git
    ```
2.  **Run the application**:
    ```bash
    mvn spring-boot:run
    ```
3.  **Access the system**:
    *   **App**: `http://localhost:8080`
    *   **H2 console**: `http://localhost:8080/h2-console`

Authored by Youssef Fellah.
Developed for the Engineering Cycle - Mundiapolis University.
