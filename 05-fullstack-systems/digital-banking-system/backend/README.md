# Digital banking backend

This robust and secure backend banking application is developed with Spring
Boot. The system integrates modern features such as JWT stateless security,
a multi-tier architecture, and an AI-powered financial assistant accessible
through a Telegram bot.

## Key features

- **Customer management:** Full CRUD operations and dynamic search
  capabilities for client profiles.
- **Account management:** Support for current accounts with overdraft limits
  and savings accounts with variable interest rates.
- **Financial operations:** Secure processing of debits, credits, and
  account-to-account transfers with strict transactional management.
- **Advanced security:** Stateless authentication using JSON Web Tokens (JWT)
  and role-based access control (RBAC) for User and Admin roles.
- **Intelligent assistant:** An interactive Telegram bot for real-time
  balance inquiries, transfers, and conversational support powered by OpenAI.

## System architecture

The project follows an N-tier architecture to ensure maintainability,
scalability, and separation of concerns.

### Directory structure

- `web`: REST controllers for HTTP entry points.
- `bot`: Telegram bot service for chat entry points.
- `services`: Business and transactional logic.
- `entities`: JPA data models.
- `repositories`: Spring Data access interfaces.
- `security`: JWT configuration and security filters.
- `dtos`: Data transfer objects for API and database isolation.
- `mappers`: Object converters using MapStruct or BeanUtils.

## Detailed layer analysis

### Data layer

Data persistence uses the single table inheritance strategy. This strategy
merges all attributes for current and savings accounts into one database table,
using a discriminator column to distinguish between account types. This
optimizes performance by avoiding complex join operations.

### Security layer

The system uses stateless security based on OAuth2 resource server standards.
By disabling traditional server-side HTTP sessions, the application improves
scalability. Spring Security intercepts requests to verify JWT signatures and
extract user roles.

### Business layer

Data integrity is maintained through Spring's `@Transactional` annotation.
This ensures that multi-step operations, such as account transfers, follow ACID
properties. If any part of a transaction fails, the system issues a rollback
to prevent data inconsistency.

### Web layer

The API uses the Data Transfer Object (DTO) design pattern. JPA entities are
not exposed directly to the web layer to prevent infinite recursion and protect
sensitive database fields.

### Bot and AI layer

Users can execute commands like `/vir [Source] [Dest] [Amount]` for money
transfers through Telegram. The bot uses the GPT-3.5 model to parse natural
language requests and provide contextual responses based on banking data.

## Installation and setup

### Prerequisites

Ensure the local environment includes Java 17, Maven, and MySQL. On Fedora,
use the following command:

```bash
sudo dnf install java-17-openjdk-devel maven mysql-server
sudo systemctl enable --now mysqld
```

### 1. Repository configuration

Clone the repository and configure environment variables by copying the
template file:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Update the `application.properties` file with your database credentials and
API keys for Telegram and OpenAI.

### 2. Application launch

Execute the following Maven command to compile and start the backend:

```bash
mvn spring-boot:run
```

The application runs at `http://localhost:8085`. Test data is injected into the
database upon startup.

## API documentation

### Authentication

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/auth/login` | Authenticate user |
| `GET` | `/auth/profile` | Retrieve the authenticated user's profile |

### Customers

| Method | Endpoint | Required role | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/customers` | USER | List all customers |
| `GET` | `/customers/search` | USER | Search customers by keyword |
| `POST` | `/customers` | ADMIN | Register a new customer |
| `DELETE` | `/customers/{id}` | ADMIN | Delete a customer record |

### Accounts and operations

| Method | Endpoint | Required role | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/accounts/{id}` | USER | Retrieve account metrics |
| `GET` | `/accounts/{id}/operations` | USER | List transaction history |
| `POST` | `/accounts/debit` | ADMIN | Execute a withdrawal |
| `POST` | `/accounts/credit` | ADMIN | Execute a deposit |
| `POST` | `/accounts/transfer` | USER | Execute a transfer |

## Technology stack

- **Language:** Java 17
- **Framework:** Spring Boot 3
- **Data access:** Spring Data JPA and Hibernate
- **Database:** MySQL 8.0+
- **Security:** Spring Security and OAuth2
- **AI and messaging:** OpenAI API and Telegram Bots API

## Credits

Developed by Youssef Fellah for the Engineering Cycle at Mundiapolis
University.
