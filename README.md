# Spring Boot portfolio

This portfolio contains a comprehensive collection of Spring Boot and Java
projects, ranging from core fundamentals to complex enterprise-grade systems.
These projects demonstrate proficiency in Inversion of Control (IoC),
Dependency Injection (DI), JPA persistence, Spring Security, and modern
full-stack integration.

## Learning progression

The portfolio follows a structured roadmap from basic Java concepts to
advanced full-stack architectures.

- **Fundamentals:** Deep dive into IoC and DI patterns.
- **Persistence:** Implementation of ORM mapping using Hibernate and Spring
  Data JPA.
- **Security:** Exploration of RBAC, JWT, and OAuth2 for secure
  authentication.
- **Web MVC:** Development of server-side rendered applications using
  Thymeleaf.
- **Full-stack:** Integration of REST APIs with modern frontend frameworks
  like Angular.

## Repository roadmap

### 01. Fundamentals

- **[java-ioc-di-lab](./01-fundamentals/java-ioc-di-lab):** Examines Inversion
  of Control (IoC) and Dependency Injection (DI). It explores the transition
  from tight coupling to dynamic configuration using Spring Context through XML
  and annotations.

### 02. Persistence and JPA

- **[spring-data-jpa-lab](./02-persistence-jpa/spring-data-jpa-lab):**
  Introduces Spring Data JPA and Hibernate, covering entity mapping,
  repository patterns, and automated schema generation.
- **[spring-jpa-hospital-management](./02-persistence-jpa/spring-jpa-hospital-management):**
  A practical application of JPA for a hospital management system featuring
  complex ORM mappings and bidirectional relationships.

### 03. Security and authentication

- **[spring-jpa-security-auth-lab](./03-security-auth/spring-jpa-security-auth-lab):**
  Establishes a foundation for Spring Security, demonstrating user-role
  management with many-to-many relationships and dynamic privilege assignment.

### 04. Web MVC

- **[mvc-security-jpa-lab](./04-web-mvc/mvc-security-jpa-lab):** A traditional
  Spring Boot MVC application using Thymeleaf, featuring JDBC/JPA security
  persistence and form validation.

### 05. Full-stack systems

- **[angular-spring-lab](./05-fullstack-systems/angular-spring-lab):**
  Integrates a Spring Boot 3 REST API with an Angular 17 frontend for product
  management.
- **[digital-banking-system](./05-fullstack-systems/digital-banking-system):**
  A comprehensive enterprise-grade banking solution featuring JWT/OAuth2
  security, financial transactions, and a rich Angular dashboard.

## Execution instructions

Each subdirectory is an independent Maven project. To execute a specific
project:

1. Navigate to the project directory:
   ```bash
   cd 05-fullstack-systems/digital-banking-system/backend
   ```
2. Run the Spring Boot application:
   ```bash
   ./mvnw spring-boot:run
   ```

## Port configuration

Most projects use the default port `8080`. To run multiple projects
simultaneously, update the `server.port` property in the respective
`application.properties` files.

## Credits

Developed by Youssef Fellah for the Engineering Cycle at Mundiapolis
University.
