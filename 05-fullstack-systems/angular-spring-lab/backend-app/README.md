# Spring Boot REST backend

This document details the implementation of a REST API developed with Spring
Boot. This application serves as the backend for a full-stack product
management project.

## Application role

The backend exposes a REST API that manages business logic and data persistence
for the `Product` entity. It acts as the source of truth for the Angular
frontend application and handles all CRUD operations.

## Technical stack

- **Framework:** Spring Boot
- **Data access:** Spring Data JPA
- **API:** Spring Web
- **Database:** H2 (in-memory)
- **Utilities:** Lombok
- **Build tool:** Maven

## Implementation details

The application follows a standard layered architecture, including controllers,
services, and repositories.

### Persistence layer

The `Product` entity represents a product in the database. Validation
annotations such as `@NotEmpty` and `@Min` ensure data integrity at the
application level.

```java
@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;
    @Min(0)
    private double price;
    private Boolean selected;
}
```

The `ProductRepository` interface uses Spring Data JPA to provide CRUD methods
without manual implementation.

### Web layer

The `ProductRestAPI` controller exposes REST endpoints for product operations.
The `@CrossOrigin("*")` annotation is used to authorize requests from the
Angular client.

```java
@RestController
@CrossOrigin("*")
@RequestMapping("/api")
@AllArgsConstructor
public class ProductRestAPI {
    private ProductService productService;

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return productService.findAll();
    }

    @DeleteMapping("/products/{id}")
    public void deleteProductById(@PathVariable(name = "id") Long id){
        productService.deleteById(id);
    }
}
```

### Data initialization

At startup, a `CommandLineRunner` inserts test data into the H2 database to
facilitate development and demonstrations.

## Database configuration

The project uses an in-memory H2 database configured in the
`application.properties` file.

```properties
spring.datasource.url=jdbc:h2:mem:product-db
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=create
```

The H2 console is available at `http://localhost:8080/h2-console` for database
inspection.

## Getting started

### 1. Launch the application

You can run the application through an IDE by executing the `main` method in
the `BackendAppApplication` class or using Maven:

```bash
mvn spring-boot:run
```

### 2. Access the API

The API is available at `http://localhost:8080`.

## Credits

Developed by Youssef Fellah for the Engineering Cycle at Mundiapolis
University.
