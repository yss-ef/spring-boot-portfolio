# 🏦 Digital Banking Backend

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.0-green?style=for-the-badge&logo=spring-boot)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)
![Security](https://img.shields.io/badge/Spring_Security-OAuth2-red?style=for-the-badge&logo=spring-security)
![OpenAI](https://img.shields.io/badge/OpenAI-GPT--3.5-412991?style=for-the-badge&logo=openai)
![Telegram](https://img.shields.io/badge/Telegram-Bot_API-26A5E4?style=for-the-badge&logo=telegram)

Bienvenue sur le projet **Digital Banking Backend**. Une application bancaire robuste, sécurisée et intelligente, développée avec **Spring Boot**. Elle intègre des fonctionnalités modernes comme la sécurité JWT, une architecture en couches stricte, et un assistant bancaire via Telegram propulsé par l'IA.

---

## 📑 Table des Matières
1.  [Fonctionnalités Clés](#-fonctionnalités-clés)
2.  [Architecture du Projet](#-architecture-globale)
3.  [Analyse Technique](#-analyse-détaillée-par-couche)
    *   [Données (JPA)](#1️⃣-couche-de-données-jpa--entities)
    *   [Sécurité (JWT)](#2️⃣-couche-de-sécurité-spring-security--jwt)
    *   [Métier (Services)](#3️⃣-couche-métier-services--transactions)
    *   [Web (API REST)](#4️⃣-couche-web-contrôleurs--dtos)
    *   [Bot & IA](#5️⃣-couche-bot--ia-telegram--openai)
4.  [Guide de Démarrage](#-installation-et-démarrage)
5.  [Documentation API](#-documentation-de-lapi)
6.  [Stack Technique](#-stack-technique)

---

## ✨ Fonctionnalités Clés

*   **Gestion des Clients** : Création, recherche, modification et suppression de clients.
*   **Gestion des Comptes** : Support des comptes **Courants** (avec découvert) et **Épargne** (avec taux d'intérêt).
*   **Opérations Bancaires** : Débits, Crédits et Virements compte-à-compte avec gestion transactionnelle.
*   **Sécurité Avancée** : Authentification Stateless via **JWT** (JSON Web Tokens) et gestion des rôles (USER/ADMIN).
*   **Assistant Intelligent** :
    *   Bot **Telegram** interactif.
    *   Consultation de solde et virements via commandes chat.
    *   Support conversationnel via **OpenAI (ChatGPT)** pour répondre aux questions financières.

---

## 🏗 Architecture Globale

Le projet respecte une architecture **N-Tiers** stricte pour garantir la maintenabilité et la scalabilité.

```mermaid
graph TD;
    Client[Client Web/Mobile/Telegram] --> Controller[Couche Web / Bot];
    Controller --> Service[Couche Service (Métier)];
    Service --> Repository[Couche DAO (Data Access)];
    Repository --> Database[(Base de Données MySQL)];
```

### Structure du Code
```
src/main/java/com/youssef/backend
├── 📂 web          # Contrôleurs REST (Points d'entrée HTTP)
├── 📂 bot          # Service Bot Telegram (Point d'entrée Chat)
├── 📂 services     # Logique métier & Transactionnelle
├── 📂 entities     # Modèle de données (JPA)
├── 📂 repositories # Interfaces d'accès aux données (Spring Data)
├── 📂 security     # Configuration JWT & Filtres de sécurité
├── 📂 dtos         # Data Transfer Objects (Isolation API/BDD)
└── 📂 mappers      # Convertisseurs (MapStruct/BeanUtils)
```

---

## 📚 Analyse Détaillée par Couche

### 1️⃣ Couche de Données (JPA & Entities)
Gestion de la persistance avec la stratégie d'héritage **Single Table**.
*   **Concept** : Une seule table `BankAccount` stocke à la fois les comptes courants et épargne, différenciés par une colonne `TYPE`.

```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", length = 4)
public abstract class BankAccount { ... }
```

### 2️⃣ Couche de Sécurité (Spring Security & JWT)
Sécurité **Stateless** basée sur les standards OAuth2 Resource Server.
*   **Flux** : Login -> Génération JWT -> Requête API + Header `Authorization: Bearer token`.
*   **Config** : Désactivation CSRF, Session Stateless, Filtres JWT.

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(ar -> ar.anyRequest().authenticated())
            .oauth2ResourceServer(oa -> oa.jwt(Customizer.withDefaults()))
            .build();
}
```

### 3️⃣ Couche Métier (Services & Transactions)
Garantie de l'intégrité des données via `@Transactional`.
*   **Exemple** : Un virement est atomique. Si le crédit échoue, le débit est annulé.

```java
@Transactional
public void transfer(String source, String dest, double amount) {
    debit(source, amount, "Virement vers " + dest);
    credit(dest, amount, "Virement de " + source);
}
```

### 4️⃣ Couche Web (Contrôleurs & DTOs)
Exposition propre des données via le pattern **DTO**.
*   Les entités JPA ne sont jamais exposées directement pour éviter les boucles infinies JSON et fuites de données.

### 5️⃣ Couche Bot & IA (Telegram & OpenAI)
Interaction utilisateur nouvelle génération.
*   **Commandes** : `/vir [Source] [Dest] [Montant]` pour les virements rapides.
*   **IA** : Le bot utilise GPT-3.5 pour analyser les demandes en langage naturel et fournir des réponses contextuelles basées sur les données du client.

---

## 🚀 Installation et Démarrage

### Prérequis
*   **Java 17** ou supérieur
*   **Maven 3.8+**
*   **MySQL 8.0+**
*   Un compte **Telegram** (pour créer un bot via BotFather)
*   Une clé API **OpenAI** (optionnel, pour l'IA)

### 1. Clonage et Configuration
```bash
git clone https://github.com/votre-repo/digital-banking-backend.git
cd digital-banking-backend
```

⚠️ **Important** : Configurez vos variables d'environnement.
Copiez le fichier d'exemple et remplissez-le :
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```
Modifiez `src/main/resources/application.properties` :
```properties
# Base de données
spring.datasource.url=jdbc:mysql://localhost:3306/BANK?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=VOTRE_MOT_DE_PASSE

# Telegram & OpenAI
telegram.bot.token=VOTRE_TOKEN_TELEGRAM
telegram.bot.username=VOTRE_BOT_USERNAME
openai.api.key=VOTRE_API_KEY_OPENAI
```

### 2. Lancement
```bash
mvn spring-boot:run
```
L'application démarrera sur `http://localhost:8085`.
Les données de test sont générées automatiquement au démarrage via `CommandLineRunner`.

---

## 📡 Documentation de l'API

### 🔐 Authentification
| Méthode | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/auth/login` | Login (Body: `{"username": "...", "password": "..."}`) |
| `GET` | `/auth/profile` | Profil utilisateur courant |

### 👤 Clients (`/customers`)
| Méthode | Endpoint | Rôle Requis | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/customers` | USER | Liste des clients |
| `GET` | `/customers/search?keyword=...` | USER | Recherche de clients |
| `POST` | `/customers` | ADMIN | Créer un client |
| `DELETE` | `/customers/{id}` | ADMIN | Supprimer un client |

### 🏦 Comptes & Opérations (`/accounts`)
| Méthode | Endpoint | Rôle Requis | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/accounts/{id}` | USER | Détails d'un compte |
| `GET` | `/accounts/{id}/operations` | USER | Historique des opérations |
| `POST` | `/accounts/debit` | ADMIN | Effectuer un débit |
| `POST` | `/accounts/credit` | ADMIN | Effectuer un crédit |
| `POST` | `/accounts/transfer` | USER | Effectuer un virement |

---

## 🛠 Stack Technique

| Catégorie | Technologie | Usage |
| :--- | :--- | :--- |
| **Langage** | Java 17 | Core |
| **Framework** | Spring Boot 3 | Structure de l'application |
| **Data** | Spring Data JPA / Hibernate | ORM & Accès BDD |
| **Base de données** | MySQL | Persistance |
| **Sécurité** | Spring Security / OAuth2 | Auth & JWT |
| **IA & Chat** | OpenAI API / Telegram Bots | Assistant Intelligent |
| **Outils** | Maven, Lombok, MapStruct | Build & Boilerplate |

---

## 🧪 Tests
Pour lancer les tests unitaires et d'intégration :
```bash
mvn test
```

---
## 👥 Crédits

*   **Réalisé par :** Youssef Fellah
*   **Encadré par :** Pr. Mohamed Youssfi

