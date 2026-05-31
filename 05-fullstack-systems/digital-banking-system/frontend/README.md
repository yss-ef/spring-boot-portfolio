# Digital banking frontend

This modern, responsive, and secure user interface is developed with Angular 17.
The frontend client provides comprehensive management of customers, bank
accounts, and financial operations through seamless communication with the
Spring Boot backend REST API.

## Key features

- **Authentication and security:** Secure login workflow via JWT, strict
  role-based access control (RBAC) for Admin and User roles, and protected
  routing using Angular route guards.
- **Administrative dashboard:** A comprehensive overview featuring key
  performance indicators (KPIs) such as total clients, accounts, and assets,
  along with interactive charts for account distribution.
- **Customer management:** Real-time search capabilities and full CRUD
  (Create, Read, Update, Delete) operations for client profiles.
- **Account management:** Native support for current accounts with overdraft
  handling and savings accounts with interest rate management.
- **Financial operations:** Dedicated interfaces for consulting paginated
  transaction histories and executing secure account-to-account transfers.

## System architecture

The project adopts a modern Angular architecture prioritizing modularity,
maintainability, and performance.

### Directory structure

- `src/app/admin-dashboard`: Dashboard UI and chart components.
- `src/app/services`: API communication for authentication, accounts, and
  customers.
- `src/app/model`: TypeScript interfaces and types (DTOs).
- `src/app/interceptor`: HTTP interceptor for injecting JWT bearer tokens.
- `src/app/customers`: Customer management interfaces.
- `src/app/accounts`: Account management interfaces.
- `src/app/operations`: Financial transaction interfaces.
- `src/app/login`: Authentication user interface.

## Technical analysis

### Interactive dashboard

The administrator dashboard uses Chart.js to transform raw banking data into
visual intelligence. It includes KPI cards for rapid insights and doughnut
charts to visualize the distribution of account types.

### Client-side security

The user interface adapts dynamically based on roles decoded from the JWT
payload. The dashboard navigation is restricted to users with the ADMIN
authority, and destructive actions are hidden from non-administrative users.

### Standalone components

The architecture exclusively uses Angular 17 standalone components. By
removing traditional `NgModule` wrappers, the codebase is simplified,
benefiting from optimized lazy-loading and faster bootstrap times.

### Reactive forms

User input and data collection are managed through reactive forms. This
ensures robust, synchronous data validation decoupled from the HTML template,
making the logic highly testable.

## Installation and setup

### Prerequisites

Ensure the local development environment includes the Node.js runtime. On
Fedora, use the following command:

```bash
sudo dnf install nodejs npm
```

Note: The digital banking backend must be running on port 8085 before
launching the frontend.

### 1. Install dependencies

Clone the repository and install the required Angular packages:

```bash
npm install
```

### 2. Launch the development server

```bash
ng serve
```

The application is accessible at `http://localhost:4200/`.

## Technology stack

- **Core framework:** Angular 17+
- **Language:** TypeScript 5.0
- **UI design:** Bootstrap 5
- **Data visualization:** Chart.js
- **Data handling:** RxJS
- **Build tools:** Angular CLI and Vite

## Testing

Execute the unit test suite using the following command:

```bash
ng test
```

## Credits

Developed by Youssef Fellah for the Engineering Cycle at Mundiapolis
University.
