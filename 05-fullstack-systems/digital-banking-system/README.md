# Digital banking platform

This high-performance, full-stack financial system features a Spring Boot
backend and an Angular frontend. The platform implements secure transaction
management, role-based access control, and automated account processing.

## System architecture

The platform follows a decoupled multi-tier architecture designed for
scalability and security.

- **Presentation tier:** Angular single-page application (SPA).
- **Application tier:** Spring Boot RESTful API with stateless authentication.
- **Data tier:** Relational persistence using MySQL and Spring Data JPA.

## Technical stack

### Backend

- **Engine:** Java 17 and Spring Boot 3
- **Security:** Spring Security, JWT (stateless authentication), and OAuth2
  resource server
- **Persistence:** Spring Data JPA, Hibernate, and MySQL
- **Build tool:** Maven

### Frontend

- **Framework:** Angular 17+
- **Styling:** Bootstrap 5 and SCSS
- **Logic:** TypeScript and RxJS
- **Icons:** Bootstrap Icons

## System features

### 1. Security and identity management

- Stateless authentication using JSON Web Tokens (JWT).
- Role-based access control (RBAC) with ADMIN and USER permissions.
- Secure password hashing and endpoint protection via Spring Security.

### 2. Financial modeling

- **Current accounts:** Management of overdraft limits for daily operations.
- **Savings accounts:** Automated interest rate calculation and application.
- **Account relationships:** Linking multiple accounts to unique customer
  profiles.

### 3. Banking operations

- **Debit and credit:** Real-time balance updates for cash withdrawals and
  deposits.
- **Transfers:** Atomic account-to-account money movement.
- **Transaction integrity:** Leveraging Spring transaction management to
  ensure data consistency during complex operations.

### 4. Data management

- Paginated transaction history for high-performance data retrieval.
- Dynamic customer search and management through CRUD operations.
- Responsive dashboards for real-time account oversight.

## Project structure

- `backend/`: Spring Boot application source code.
- `frontend/`: Angular application source code.
- `screenshots/`: UI/UX documentation.

## Deployment and setup

### Prerequisites

- Java 17 (OpenJDK)
- Maven 3.8+
- Node.js 18+ and Angular CLI
- MySQL server

### 1. Backend initialization

1. Create the database: `CREATE DATABASE digital_banking_db;`
2. Configure credentials in `backend/src/main/resources/application.properties`.
3. Run the server:
   ```bash
   cd backend
   mvn spring-boot:run
   ```

### 2. Frontend initialization

1. Install dependencies:
   ```bash
   cd frontend
   npm install
   ```
2. Start the application:
   ```bash
   ng serve
   ```

## Credits

Developed by Youssef Fellah for the Engineering Cycle at Mundiapolis
University.
