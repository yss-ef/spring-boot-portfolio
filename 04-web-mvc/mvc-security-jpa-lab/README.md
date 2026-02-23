# 🌐 Web Application with Spring Boot, Spring Security & JPA

> **Practical Work (TP) Report #3**
> This project focuses on the development of a complete web application for product management. The primary objective was to implement a multi-tier architecture using **Spring Boot**, with data persistence managed by **Spring Data JPA** and access control secured via **Spring Security**.

## 📑 Table of Contents

* [Application Showcase](https://www.google.com/search?q=%23-application-showcase)
* [Key Features](https://www.google.com/search?q=%23-key-features)
* [Technology Stack](https://www.google.com/search?q=%23%EF%B8%8F-technology-stack)
* [Implementation Details](https://www.google.com/search?q=%23-implementation-details)
* [Database Configuration](https://www.google.com/search?q=%23-database-configuration)
* [Troubleshooting](https://www.google.com/search?q=%23-troubleshooting)

## 📸 Application Showcase

| Login Page | Product Catalog |
| --- | --- |
| <img src="/screenshots/login.png" width="400" alt="Login Page"><br>

<br>*Secure authentication portal.* | <img src="/screenshots/products.png" width="400" alt="Products Screen"><br>

<br>*Dynamic list with role-based actions.* |

| Add New Product | Update Product |
| --- | --- |
| <img src="/screenshots/new.png" width="400" alt="Creation Page"><br>

<br>*Validated input form.* | <img src="/screenshots/update.png" width="400" alt="Update Page"><br>

<br>*Pre-filled modification form.* |

## ✨ Key Features

The application implements full **CRUD** (Create, Read, Update, Delete) operations on a "Product" entity. Access is strictly controlled through role-based authorization:

* **Standard Users (`USER` role):** Authorized to browse and view the product catalog.
* **Administrators (`ADMIN` role):** Authorized for full administrative control (View, Add, Edit, and Delete products).
* **Security:** A standard login portal provided by Spring Security manages the authentication flow.

## 🛠️ Technology Stack

| Category | Technology |
| --- | --- |
| **Backend** | Spring Boot, Spring MVC, Spring Data JPA, Spring Security |
| **Frontend** | Thymeleaf, Thymeleaf Layout Dialect, Bootstrap 5 |
| **Database** | H2 (In-memory) for rapid development / MySQL (Optional) |
| **Build Tool** | Maven |
| **Server** | Embedded Apache Tomcat |

## 🏗️ Implementation Details

The application architecture is divided into clear logical layers: Entities, Repositories, Services, and Controllers.

### 1. Persistence Layer (JPA)

#### Entity: `Product.java`

The `Product` class maps to the database table. It utilizes Bean Validation annotations (`@NotEmpty`, `@Min`) to enforce data integrity.

```java
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty @Size(min = 3, max = 50)
    private String name;
    
    @Min(1)
    private double price;
    
    private int quantity;
}

```

### 2. Security Configuration

`SecurityConfig.java` defines the authentication and authorization rules. This lab uses **In-Memory Authentication** for simplicity.

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(Customizer.withDefaults()) // Standard login page
                .authorizeHttpRequests(ar -> ar
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/user/**").hasRole("USER")
                    .anyRequest().authenticated()
                )
                .build();
    }
}

```

## 🚀 Database Configuration

While initially configured for MySQL, the project was migrated to **H2** for better portability and zero-configuration during the development phase.

**`application.properties` snippet:**

```properties
spring.datasource.url=jdbc:h2:mem:product-db
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# Enable H2 Console for debugging
spring.h2.console.enabled=true

# Schema generation strategy
spring.jpa.hibernate.ddl-auto=update

```

*The console is accessible at: `http://localhost:8080/h2-console*`

## 🛠️ Troubleshooting

1. **Database Connection Issues:** Initially, the app threw a `Communications link failure` error when connecting to XAMPP/MySQL.
* **Solution:** Switched the datasource to **H2** (In-memory), resolving all connectivity issues and allowing focus on business logic.


2. **Missing Bootstrap Styles:** CSS files were not loading on nested URLs like `/user/products`.
* **Solution:** Corrected paths in the Thymeleaf template using the `@{/...}` syntax (`th:href="@{/webjars/...}"`) to ensure absolute path generation relative to the app context.



## 🎯 Conclusion

This lab successfully consolidated core Spring ecosystem skills by building a functional web application from scratch. Key takeaways include:

* Efficiently managing data persistence with **Spring Data JPA**.
* Securing web routes and managing role-based access with **Spring Security**.
* Developing dynamic and modular UI components with **Thymeleaf** and **Bootstrap**.
* Debugging environment-specific configuration issues in a Spring Boot context.

---

*Authored by Youssef Fellah.*

*Developed as part of the 2nd year Engineering Cycle - Mundiapolis University.*

