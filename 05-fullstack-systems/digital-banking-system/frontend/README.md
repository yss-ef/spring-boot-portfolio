# 🏦 Digital Banking Frontend

> A modern, responsive, and secure user interface developed with **Angular 17+**. This frontend client provides comprehensive management of customers, bank accounts, and financial operations by communicating seamlessly with the Spring Boot backend REST API.

## 📑 Table of Contents

* [Key Features](https://www.google.com/search?q=%23-key-features)
* [System Architecture](https://www.google.com/search?q=%23%EF%B8%8F-system-architecture)
* [Technical Analysis](https://www.google.com/search?q=%23-technical-analysis)
* [Application Structure](https://www.google.com/search?q=%23-application-structure)
* [Installation & Setup](https://www.google.com/search?q=%23-installation--setup)
* [Technology Stack](https://www.google.com/search?q=%23%EF%B8%8F-technology-stack)
* [Credits](https://www.google.com/search?q=%23-credits)

## ✨ Key Features

* **🔐 Authentication & Security:** Secure login workflow via **JWT**, strict Role-Based Access Control (Admin/User), and protected routing using Angular Route Guards.
* **📊 Administrative Dashboard:** A comprehensive overview featuring Key Performance Indicators (Total clients, accounts, total assets) and interactive charts detailing account distribution and balances.
* **👥 Customer Management:** Real-time search capabilities alongside full CRUD (Create, Read, Update, Delete) operations for client profiles.
* **🏦 Account Management:** Native interface support for **Current Accounts** (handling overdrafts) and **Saving Accounts** (handling interest rates).
* **💸 Financial Operations:** Dedicated interfaces for consulting paginated transaction histories and executing secure account-to-account transfers.

## 🏗️ System Architecture

The project adopts a **Modern Angular Architecture**, heavily prioritizing modularity, ease of maintenance, and rendering performance.

### Directory Structure

```text
src/app
├── 📂 admin-dashboard # Dashboard UI and Chart components
├── 📂 services        # API communication (Auth, Accounts, Customers)
├── 📂 model           # TypeScript Interfaces and Types (DTOs)
├── 📂 interceptor     # HTTP Interceptor for injecting JWT Bearer tokens
├── 📂 customers       # Customer management interfaces
├── 📂 accounts        # Account management interfaces
├── 📂 operations      # Financial transaction interfaces
└── 📂 login           # Authentication UI

```

## 📚 Technical Analysis

### 1️⃣ Interactive Dashboard (Chart.js)

The administrator dashboard leverages **Chart.js** to transform raw banking data into visual intelligence. It features:

* **KPI Cards:** Rapid insights into core metrics.
* **Doughnut Charts:** Visualizing the proportional distribution of account types (Current vs. Savings).
* **Bar Charts:** Comparing total financial assets locked within different account categories.

### 2️⃣ Client-Side Role-Based Security

The user interface dynamically adapts based on the roles extracted and decoded directly from the JWT payload:

* The **Dashboard** navigation link is strictly rendered only for users possessing the `ADMIN` authority.
* Destructive actions (like the **Delete** or **Edit** buttons on customer profiles) are structurally hidden from non-administrative users.

### 3️⃣ Standalone Components

The architecture exclusively utilizes Angular 17 **Standalone Components**. By completely removing traditional `NgModule` wrappers, the codebase is significantly simplified, and the application benefits from highly optimized lazy-loading and faster bootstrap times.

### 4️⃣ Reactive Forms

Data collection and user input are managed entirely via **Reactive Forms**. This paradigm ensures robust, synchronous data validation that is completely decoupled from the HTML template, making the logic highly testable.

## 📱 Application Structure

### 🔐 Authentication (`/login`)

* **Description:** Secure login form designed to authenticate credentials and securely store the returned JWT in the browser's local storage.

### 📊 Admin Dashboard (`/admin`)

* **Access:** Strictly reserved for Administrators.
* **Functions:** Global statistical visualization of the bank's operational health.

### 👤 Customers (`/customers`)

* **Functions:** Paginated list of clients, live search filtering, and management actions (CRUD).

### 🏦 Accounts & Operations (`/accounts`)

* **Functions:** Direct account consultation, live balance rendering, and historical operation tracking.

## 🚀 Installation & Setup

### Prerequisites (Fedora 43)

Ensure your local development environment is configured with the required Node runtime:

```bash
# Install Node.js and NPM
sudo dnf install nodejs npm

```

*Note: Ensure the Digital Banking Backend is actively running on port `8085` before launching the frontend.*

### 1. Install Dependencies

Clone the repository and install the required Angular packages:

```bash
npm install

```

### 2. Launch the Development Server

```bash
ng serve

```

The application will compile and be instantly accessible at `http://localhost:4200/`.

## 🛠️ Technology Stack

| Category | Technology | Purpose |
| --- | --- | --- |
| **Core Framework** | Angular 17+ | Frontend SPA architecture |
| **Language** | TypeScript 5.0 | Strict typing and business logic |
| **UI/UX Design** | Bootstrap 5 | Component styling and responsive grid |
| **Data Visualization** | Chart.js | Rendering interactive dashboard graphics |
| **Data Handling** | RxJS | Reactive programming and asynchronous streams |
| **Build Tools** | Angular CLI / Vite | Development server and production bundling |

## 🧪 Testing

To execute the suite of unit tests:

```bash
ng test

```

---

*Authored by Youssef Fellah.*

*Developed as part of the 2nd year Engineering Cycle - Mundiapolis University.*
