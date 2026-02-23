# 🔄 Java IoC & Dependency Injection Lab

> **Academic Practical Work (TP) Report**
> This repository contains the practical implementation of **Inversion of Control (IoC)** and **Dependency Injection (DI)** principles in Java. The primary objective is to build a simple application demonstrating **loose coupling** to ensure high maintainability and scalability.

## 📑 Table of Contents

* [Project Assignment](https://www.google.com/search?q=%23-project-assignment)
* [Application Architecture](https://www.google.com/search?q=%23%EF%B8%8F-application-architecture)
* [Implementation Steps](https://www.google.com/search?q=%23%EF%B8%8F-implementation-steps)
* [Dependency Injection Methods](https://www.google.com/search?q=%23-dependency-injection-methods)
* [Local Setup (Fedora)](https://www.google.com/search?q=%23-local-setup)
* [Conclusion & Takeaways](https://www.google.com/search?q=%23-conclusion--takeaways)

## 📋 Project Assignment

The directive was to reproduce the concepts covered in the course materials by following a strict iterative process:

1. Create an `IDao` interface with a `getData` method.
2. Create a concrete implementation of this interface.
3. Create an `IMetier` interface with a `calculate` method.
4. Create an implementation of this interface utilizing **loose coupling**.
5. Inject the dependencies using three distinct approaches:
* Static Instantiation.
* Dynamic Instantiation.
* The Spring Framework (both XML and Annotation-based versions).



## 🏗️ Application Architecture

To satisfy the **loose coupling** requirement, the application's architecture is strictly interface-driven. Classes depend on contracts rather than concrete implementations.

* **`IDao`**: The Data Access Object layer interface. It defines a generic contract for retrieving data (`double getData()`).
* **`IMetier`**: The Business Logic layer interface. It defines a contract for executing calculations (`double calculate()`).

The `MetierImpl` class depends solely on `IDao`, not on a specific database implementation like `DaoImpl`. This architectural choice makes the application **open for extension but closed for modification** (OCP principle). You can swap the data source (e.g., from a database to a REST API) without altering a single line of the business logic.

## ⚙️ Implementation Steps

### 1. Defining the Interfaces

**`IDao.java` & `IMetier.java**`
These files establish the contracts. They contain no logic, only method signatures.

```java
package org.yss.dao;
public interface IDao {
    public double getData();
}

package org.yss.metier;
public interface IMetier {
    public double calculate();
}

```

### 2. Concrete Implementations

**`DaoImpl.java`**
A simulated data access class. The `@Component("d")` annotation will later tell Spring to manage this class.

```java
package org.yss.dao;
import org.springframework.stereotype.Component;

@Component("d")
public class DaoImpl implements IDao {
    @Override
    public double getData() {
        System.out.println("Using the Database");
        return 31;
    }
}

```

**`MetierImpl.java`**
The business logic implementation. Notice the `IDao dao` property. The `MetierImpl` does not instantiate the DAO itself using `new DaoImpl()`; it expects it to be provided (injected).

```java
package org.yss.metier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yss.dao.IDao;

@Component
public class MetierImpl implements IMetier {
    @Autowired
    IDao dao; // Loose coupling: depends on the interface

    @Override
    public double calculate() {
        double t = dao.getData();
        return t * 21 * Math.PI;
    }

    // Setter required for XML/Dynamic injection
    public void setDao(IDao dao) {
        this.dao = dao;
    }
}

```

## 💉 Dependency Injection Methods

The core challenge is securely passing an instance of `IDao` into `MetierImpl`. Here is how it evolves from basic to enterprise-grade.

### A. Static Instantiation (Tight Assembly)

The simplest approach, but the least flexible. The developer hardcodes the instantiation in the `main` method. Changing the DAO implementation requires editing this code and recompiling the application.

```java
public class Pres {
    public static void main(String[] args) {
        IDao dao = new DaoImpl();           // Hardcoded instantiation
        IMetier metier = new MetierImpl(dao); // Constructor injection
        System.out.println(metier.calculate());
    }
}

```

### B. Dynamic Instantiation (Java Reflection)

This method achieves true runtime flexibility. The exact classes to instantiate are read from an external `config.txt` file.

**`config.txt` content:**

```text
org.yss.ext.DaoImpl2
org.yss.metier.MetierImpl

```

**Implementation:**
By using `Class.forName()`, the JVM loads the classes dynamically at runtime. We then use reflection (`newInstance()`) to create objects without ever hardcoding their names in the Java file.

```java
public class Pres2 {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("config.txt"));

        // 1. Dynamically load and instantiate the DAO
        String daoClassName = sc.nextLine();
        Class cDao = Class.forName(daoClassName);
        IDao dao = (IDao) cDao.newInstance();

        // 2. Dynamically load the Metier class and inject the DAO via constructor
        String metierClassName = sc.nextLine();
        Class cMetier = Class.forName(metierClassName);
        IMetier metier = (IMetier) cMetier.getConstructor(IDao.class).newInstance(dao);

        System.out.println(metier.calculate());
    }
}

```

### C. The Spring Framework Approach

In modern enterprise applications, we delegate the entire lifecycle and injection process to an IoC Container (Spring).

#### XML Configuration

We map the objects (beans) and their relationships in an XML file. Spring reads this file, creates the instances, and wires them together via setter methods (`<property>`).

**`config.xml` snippet:**

```xml
<bean id="d" class="org.yss.dao.DaoImpl"></bean>
<bean id="metier" class="org.yss.metier.MetierImpl">
    <property name="dao" ref="d"></property> </bean>

```

#### Annotation Configuration (Modern Standard)

XML is replaced by inline metadata. Spring scans the specified packages.

* `@Component`: Flags the class as a Spring-managed bean.
* `@Autowired`: Tells Spring to find a matching bean (like `DaoImpl`) and automatically inject it into the field.

```java
public class PresSpringAnnotation {
    public static void main(String[] args) {
        // Start Spring context and scan specific packages for annotations
        ApplicationContext ctx = new AnnotationConfigApplicationContext("org.yss.dao", "org.yss.metier");
        
        // Retrieve the fully assembled bean
        IMetier metier = ctx.getBean(IMetier.class);
        System.out.println(metier.calculate());
    }
}

```

## 💻 Local Setup

To run this project on your Fedora environment:

```bash
# Ensure Java and Maven are installed
sudo dnf install java-17-openjdk maven

# Clone the repository
git clone https://github.com/yss-ef/[REPO_NAME].git
cd [REPO_NAME]

# Compile and run the Annotation version using Maven
mvn compile exec:java -Dexec.mainClass="org.yss.pres.PresSpringAnnotation"

```

## 🎯 Conclusion & Takeaways

This lab successfully demonstrates the critical importance of Inversion of Control in creating modular software.

By transitioning from static instantiation to reflection-based dynamic instantiation, and finally to the Spring Framework, the evolution of flexibility becomes evident. While manual dynamic injection reveals the underlying mechanics of IoC, leveraging an enterprise framework like Spring automates object lifecycle management, allowing engineers to focus entirely on business logic. The modern annotation-based approach proves to be the most efficient and readable paradigm.

---

*Authored by Youssef Fellah.*

*Developed as part of the 2nd year Engineering Cycle - Mundiapolis University.*
