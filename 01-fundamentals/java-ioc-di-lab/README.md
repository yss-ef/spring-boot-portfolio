# Java IoC & Dependency Injection: Architectural Deep-Dive

A technical exploration of Inversion of Control (IoC) and Dependency Injection (DI) patterns in Java. This project demonstrates the evolution from tightly coupled systems to highly flexible, interface-driven architectures using manual reflection and the Spring Framework.

## Architectural Overview

The project implements a decoupled service-layer architecture designed to satisfy the Open/Closed Principle (OCP). By depending on abstractions rather than concrete implementations, the system allows for runtime modifications without source code alteration.

### Core Contracts
*   **IDao**: Data Access Object interface defining the data retrieval contract.
*   **IMetier**: Business logic interface defining the computational contract.

---

## Dependency Injection Paradigms

### 1. Static Instantiation (Tight Coupling)
*   **Mechanism**: Manual object creation via the `new` keyword.
*   **Analysis**: High coupling; changing implementations requires recompilation.
*   **Example**: `IDao dao = new DaoImpl();`

### 2. Dynamic Instantiation (Reflection API)
*   **Mechanism**: Utilizing Java's `Class.forName()` and `newInstance()` to load classes from external configuration files (`config.txt`).
*   **Analysis**: Achieves loose coupling at the JVM level. The application remains open for extension (new DAO implementations) without modifying the main execution logic.

### 3. Spring IoC Container (Framework Orchestration)
*   **XML Configuration**: Declarative bean management and wiring using setter injection.
*   **Annotation-based Configuration**: Modern metadata-driven injection using `@Component` and `@Autowired`. This represents the current industry standard for enterprise Java development.

---

## Technical Stack

*   **Language**: Java 17+
*   **Framework**: Spring Core (Context, Beans)
*   **Build Tool**: Maven
*   **Principles**: S.O.L.I.D (specifically OCP and DIP)

---

## Project Structure

```text
├── src/main/java/org/yss/
│   ├── dao/        # Data Access layer (Interfaces & Impls)
│   ├── metier/     # Business logic layer
│   ├── ext/        # Alternative implementations for testing flexibility
│   └── pres/       # Presentation layer (Static, Dynamic, and Spring runners)
├── config.txt      # Configuration for Dynamic Reflection
├── config.xml      # Spring XML configuration
└── pom.xml         # Project dependencies
```

---

## Setup & Execution

### Prerequisites
*   JDK 17 or higher
*   Apache Maven 3.8+

### Execution Sequences

**Dynamic Reflection Runner:**
```bash
mvn compile exec:java -Dexec.mainClass="org.yss.pres.PresDepFaibleClassique"
```

**Spring Annotation Runner:**
```bash
mvn compile exec:java -Dexec.mainClass="org.yss.pres.PresSpringAnnotation"
```

---

*Authored by Youssef Fellah.*

*Developed for the Engineering Cycle - Mundiapolis University.*
