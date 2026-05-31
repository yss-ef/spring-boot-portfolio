# Full-stack orchestration: Angular and Spring Boot integration

Bottom Line Up Front: This project provides a decoupled, multi-tier web
application demonstrating the integration of a reactive Angular frontend with a
robust Spring Boot REST API. It focuses on asynchronous data flow, cross-origin
resource sharing (CORS) management, and modular full-stack architecture.

## Technical architecture

The system follows a modern client-server paradigm, ensuring a strict
separation of concerns between the presentation and application layers:

1.  **Frontend (client)**: A Single Page Application (SPA) built with Angular,
    utilizing reactive programming patterns for dynamic state management.
2.  **Backend (server)**: A RESTful API engineered with Spring Boot, managing
    business logic and relational data persistence.
3.  **Communication**: Asynchronous data exchange via HTTP/REST with JSON as the
    primary payload format.

---

## Technical stack

### Backend
*   **Framework**: Spring Boot 3
*   **Persistence**: Spring Data JPA / Hibernate
*   **Database**: H2 In-Memory (Development)
*   **API design**: RESTful Controllers with CORS orchestration
*   **Build tool**: Maven

### Frontend
*   **Framework**: Angular 17+
*   **State management**: RxJS (Observables)
*   **Styling**: Bootstrap 5 / SCSS
*   **Client**: HttpClientModule for asynchronous service integration

---

## Core implementations

### 1. Decoupled service architecture
*   **CORS management**: Implementation of `@CrossOrigin` policies at the
    controller level to enable secure, multi-origin communication between the
    Angular client (`localhost:4200`) and the Spring API (`localhost:8080`).
*   **Reactive services**: Angular services designed to encapsulate `HttpClient`
    logic, providing clean, observable data streams to UI components.

### 2. Data integrity and persistence
*   **JPA modeling**: Precise mapping of relational entities with automated
    schema synchronization via Hibernate.
*   **Bean validation**: Use of JSR-303 annotations (`@NotEmpty`, `@Min`) to
    ensure data validity before database persistence.
*   **Transactional services**: Leveraging Spring's service-layer abstraction for
    atomic business operations.

### 3. Reactive UI synchrony
*   **Change detection**: Strategic use of `ChangeDetectorRef` to ensure the DOM
    remains synchronized with high-frequency asynchronous API responses.
*   **Staggered loading**: Implementing subscription-based data fetching to
    maintain a fluid user experience during network latency.

---

## Project structure

```text
├── backend-app/  # Spring Boot REST API source
├── angular-app/  # Angular Single Page Application source
└── README.md     # System documentation
```

---

## Deployment and setup

### Prerequisites
*   Java 17 (OpenJDK)
*   Maven 3.8+
*   Node.js 18+ and Angular CLI

### 1. Backend initialization
```bash
cd backend-app
mvn spring-boot:run
```
*   **API endpoint**: `http://localhost:8080/api/products`
*   **Database console**: `http://localhost:8080/h2-console`

### 2. Frontend initialization
```bash
cd angular-app
npm install
ng serve
```
*   **Application gateway**: `http://localhost:4200`

Authored by Youssef Fellah.
Developed for the Engineering Cycle - Mundiapolis University.
