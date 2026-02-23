# 🌐 Full-Stack Application with Angular & Spring Boot

> **Practical Work (TP) Report #4**
> This project focuses on developing a full-stack web application with a decoupled architecture. The goal was to build a reactive user interface with **Angular** that communicates with a **Spring Boot** REST API for product management.

## 📑 Table of Contents

* [Application Architecture](https://www.google.com/search?q=%23-application-architecture)
* [Technology Stack](https://www.google.com/search?q=%23%EF%B8%8F-technology-stack)
* [Implementation Details](https://www.google.com/search?q=%23-implementation-details)
* [Database Configuration](https://www.google.com/search?q=%23-database-configuration)
* [Troubleshooting](https://www.google.com/search?q=%23-troubleshooting)
* [Conclusion](https://www.google.com/search?q=%23-conclusion)

## 🏗️ Application Architecture

The application follows a modern client-server architecture:

* **Backend (Server)**: A REST API developed with **Spring Boot**. It handles business logic and data access, exposing endpoints for CRUD operations.
* **Frontend (Client)**: A Single Page Application (SPA) developed with **Angular**. It consumes the backend API to display data and handle user interactions.

This clear separation of concerns allows for independent development and deployment of the two tiers.

## 🛠️ Technology Stack

| Category | Technology |
| --- | --- |
| **Backend** | Spring Boot, Spring Data JPA, Spring Web, Lombok, Maven |
| **Frontend** | Angular, HttpClientModule, Bootstrap 5, Bootstrap Icons, npm |
| **Database** | H2 (In-memory) |

## 🏗️ Implementation Details

### 1. Backend Layer (Spring Boot)

The backend architecture is divided into logical layers: Entity, Repository, and REST Controller.

#### Persistence Layer (JPA)

**Entity: `Product.java**`
Uses JPA annotations for ORM mapping and Bean Validation to ensure data integrity.

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

**Repository: `ProductRepository.java**`
Extends `JpaRepository` to provide standard CRUD operations out of the box.

```java
public interface ProductRepository extends JpaRepository<Product, Long> {
}

```

#### Web Layer (REST API)

**Controller: `ProductRestAPI.java**`
Exposes REST endpoints. The `@CrossOrigin("*")` annotation is critical for allowing the Angular application to communicate with the API across different ports.

```java
@RestController
@CrossOrigin("*") // Essential for Angular communication
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

### 2. Frontend Layer (Angular)

The frontend is built using components and services to maintain a clean separation of concerns.

#### Data Service: `ProductService.ts`

Encapsulates API communication using Angular's `HttpClient`.

```typescript
@Injectable({ providedIn: 'root' })
export class ProductService {
  constructor(private http: HttpClient) {}

  getAllProducts() {
      return this.http.get("http://localhost:8080/api/products");
  }

  deleteProduct(p: any) {
      return this.http.delete("http://localhost:8080/api/products/" + p.id);
  }
}

```

#### Display Component: `products.ts`

Injects the `ProductService` to fetch data. It uses `ChangeDetectorRef` to ensure the UI stays synchronized with the data stream.

```typescript
@Component({ /* ... */ })
export class Products implements OnInit {
  products: any = [];

  constructor(private productService: ProductService, private cd: ChangeDetectorRef) {}

  ngOnInit() {
    this.getAllProducts();
  }

  getAllProducts() {
    this.productService.getAllProducts().subscribe({
      next: resp => {
        this.products = resp;
        this.cd.detectChanges(); // Force UI refresh
      }
    });
  }

  handleDelete(p: any) {
    this.productService.deleteProduct(p).subscribe({
      next: () => this.getAllProducts() // Reload list after deletion
    });
  }
}

```

## 🚀 Database Configuration

The backend uses **H2** for zero-configuration development.

**`application.properties` snippet:**

```properties
spring.datasource.url=jdbc:h2:mem:product-db
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=create

```

*Access the DB manager at: `http://localhost:8080/h2-console*`

## 🛠️ Troubleshooting

* **CORS Issues**: Browser security initially blocked requests from `localhost:4200` to `localhost:8080`.
* **Solution**: Added `@CrossOrigin("*")` to the Spring Boot REST controller to allow cross-origin resource sharing.


* **UI Refresh Latency**: Sometimes the Angular view didn't update immediately after data retrieval.
* **Solution**: Injected `ChangeDetectorRef` and manually triggered `detectChanges()` after successful API responses to ensure the DOM reflected the latest state.



## 🎯 Conclusion

This project served as an excellent introduction to modern full-stack development. Key takeaways include:

* Building a robust, decoupled REST API with **Spring Boot**.
* Creating a reactive, dynamic UI with **Angular**.
* Managing asynchronous communication and overcoming common hurdles like **CORS** and **Change Detection** strategies.

---

*Authored by Youssef Fellah.*

*Developed as part of the 2nd year Engineering Cycle - Mundiapolis University.*
