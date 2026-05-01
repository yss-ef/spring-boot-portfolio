# Digital Banking Platform

A high-performance, full-stack financial system featuring a robust Spring Boot backend and a modern Angular frontend. This platform implements secure transaction management, role-based access control, and automated account processing.

## System Architecture

The platform follows a decoupled Multi-Tier Architecture designed for scalability and security:

1.  **Presentation Tier**: Angular Single Page Application (SPA).
2.  **Application Tier**: Spring Boot RESTful API with stateless authentication.
3.  **Data Tier**: Relational persistence using MySQL and Spring Data JPA.

---

## Technical Stack

### Backend
*   **Engine**: Java 17 / Spring Boot 3
*   **Security**: Spring Security, JWT (Stateless Auth), OAuth2 Resource Server
*   **Persistence**: Spring Data JPA, Hibernate, MySQL
*   **Build Tool**: Maven

### Frontend
*   **Framework**: Angular 17+
*   **Styling**: Bootstrap 5 / SCSS
*   **Logic**: TypeScript, RxJS (Reactive Programming)
*   **Icons**: Bootstrap Icons

---

## System Features

### 1. Security & Identity Management
*   Stateless authentication using JSON Web Tokens (JWT).
*   Role-Based Access Control (RBAC): ADMIN and USER permissions.
*   Secure password hashing and endpoint protection via Spring Security.

### 2. Financial Modeling
*   **Current Accounts**: Management of overdraft limits for daily operations.
*   **Savings Accounts**: Automated interest rate calculation and application.
*   **Account Relationships**: Linking multiple accounts to unique customer profiles.

### 3. Banking Operations
*   **Debit/Credit**: Real-time balance updates for cash withdrawals and deposits.
*   **Transfers**: Atomic account-to-account money movement.
*   **Transaction Integrity**: Leveraging Spring Transaction Management (@Transactional) to ensure data consistency during complex operations.

### 4. Data Management
*   Paginated transaction history for high-performance data retrieval.
*   Dynamic customer search and management (CRUD operations).
*   Responsive dashboards for real-time account oversight.

---

## Project Structure

```text
├── backend/    # Spring Boot application source
├── frontend/   # Angular application source
├── screenshots/ # UI/UX documentation
└── README.md   # System documentation
```

---

## Deployment & Setup

### Prerequisites
*   Java 17 (OpenJDK)
*   Maven 3.8+
*   Node.js 18+ & Angular CLI
*   MySQL Server

### 1. Backend Initialization
1. Create the database: `CREATE DATABASE digital_banking_db;`
2. Configure credentials in `backend/src/main/resources/application.properties`.
3. Run the server:
   ```bash
   cd backend
   mvn spring-boot:run
   ```

### 2. Frontend Initialization
1. Install dependencies:
   ```bash
   cd frontend
   npm install
   ```
2. Start the application:
   ```bash
   ng serve
   ```

---

*Authored by Youssef Fellah.*

*Developed for the Engineering Cycle - Mundiapolis University.*
