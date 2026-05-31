# Java IoC and dependency injection: architectural deep-dive

Bottom Line Up Front: This project provides a technical exploration of Inversion
of Control (IoC) and Dependency Injection (DI) patterns in Java. It
demonstrates the evolution from tightly coupled systems to flexible,
interface-driven architectures using manual reflection and the Spring Framework.

## Architectural overview

The project implements a decoupled service-layer architecture designed to
satisfy the Open/Closed Principle (OCP). By depending on abstractions rather
than concrete implementations, the system allows for runtime modifications
without source code alteration.

### Core contracts
*   **IDao**: Data Access Object interface defining the data retrieval contract.
*   **IMetier**: Business logic interface defining the computational contract.

---

## Dependency injection paradigms

### 1. Static instantiation (tight coupling)
*   **Mechanism**: Manual object creation via the `new` keyword.
*   **Analysis**: High coupling; changing implementations requires recompilation.
*   **Example**: `IDao dao = new DaoImpl();`

### 2. Dynamic instantiation (reflection API)
*   **Mechanism**: Utilizing Java's `Class.forName()` and `newInstance()` to load
    classes from external configuration files (`config.txt`).
*   **Analysis**: Achieves loose coupling at the JVM level. The application
    remains open for extension (new DAO implementations) without modifying the
    main execution logic.

### 3. Spring IoC container (framework orchestration)
*   **XML configuration**: Declarative bean management and wiring using setter
    injection.
*   **Annotation-based configuration**: Modern metadata-driven injection using
    `@Component` and `@Autowired`. This represents the current industry standard
    for enterprise Java development.

---

## Technical stack

*   **Language**: Java 17+
*   **Framework**: Spring Core (Context, Beans)
*   **Build Tool**: Maven
*   **Principles**: S.O.L.I.D (specifically OCP and DIP)

---

## Project structure

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

## Setup and execution

### Prerequisites
*   JDK 17 or higher
*   Apache Maven 3.8+

### Execution sequences

**Dynamic reflection runner:**
```bash
mvn compile exec:java -Dexec.mainClass="org.yss.pres.PresDepFaibleClassique"
```

**Spring annotation runner:**
```bash
mvn compile exec:java -Dexec.mainClass="org.yss.pres.PresSpringAnnotation"
```

Authored by Youssef Fellah.
Developed for the Engineering Cycle - Mundiapolis University.
