# 🏦 Digital Banking Project

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Angular](https://img.shields.io/badge/Angular-17-DD0031?style=for-the-badge&logo=angular&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white)

Bienvenue sur le dépôt global du projet **Digital Banking**. Ce projet est une solution complète de banque numérique ("Digital Banking"), composée d'un backend robuste basé sur Spring Boot et d'un frontend moderne développé avec Angular.

---

## 📑 Table des Matières
- [Architecture Globale](#-architecture-globale)
- [Fonctionnalités](#-fonctionnalités-principales)
- [Technologies Utilisées](#-technologies)
- [Captures d'écran](#-captures-décran)
- [Démarrage Rapide](#-démarrage-rapide)
- [Crédits](#-crédits)

---

## 🏗 Architecture Globale

Le système suit une architecture **Multi-Tiers** moderne :
1.  **Frontend (SPA)** : Application Angular communiquant via HTTP/REST.
2.  **Backend (API)** : Spring Boot exposant des services RESTful sécurisés.
3.  **Data** : Base de données relationnelle MySQL.

### 🔙 Backend (Spring Boot)
Le backend gère la persistance, la sécurité (JWT) et la logique métier (virements, comptes).
*   [Voir le README du Backend](./backend/README.md)

### 🖥️ Frontend (Angular)
Interface utilisateur réactive pour la gestion des clients et des comptes bancaires.
*   [Voir le README du Frontend](./frontend/README.md)

---

## 🔐 Fonctionnalités Principales

*   **Sécurité & Authentification** :
    *   Authentification Stateless avec **JWT (JSON Web Tokens)**.
    *   Gestion des rôles (ADMIN, USER).
*   **Gestion des Clients** :
    *   Recherche de clients (Search bar).
    *   Ajout, modification et suppression de clients.
*   **Gestion des Comptes Bancaires** :
    *   Support des comptes **Courants** (avec découvert) et **Épargne** (avec taux d'intérêt).
    *   Consultation du solde et des détails.
*   **Opérations Bancaires** :
    *   **Débit** (Retrait).
    *   **Crédit** (Dépôt).
    *   **Virement** (Transfert compte à compte).
*   **Historique** :
    *   Consultation des opérations passées sur un compte.

---

## 🛠 Technologies

### Backend
*   **Langage** : Java 17
*   **Framework** : Spring Boot 3
*   **Data** : Spring Data JPA, Hibernate, MySQL
*   **Sécurité** : Spring Security, OAuth2 Resource Server / JWT

### Frontend
*   **Framework** : Angular 17+
*   **Langage** : TypeScript
*   **UI** : Bootstrap 5, Icons Bootstrap
*   **State/Async** : RxJS

---

## 📸 Captures d'écran

*Veuillez ajouter vos captures dans un dossier `screenshots/` à la racine.*

### Page d'Authentification
![Login Page](./screenshots/login.png)

### Page d'Acceuil
![Home Page](./screenshots/home.png)


### Gestion des Clients
![Customers Page](./screenshots/customers.png)
![Customers add Page](./screenshots/customers-add.png)


### Comptes & Opérations
![Accounts Page](./screenshots/accounts.png)
![Accounts add cur Page](./screenshots/accounts-add-cur.png)
![Accounts add sav Page](./screenshots/accounts-add-sav.png)
![Transfer Page](./screenshots/transfer.png)


---

## 🚀 Démarrage Rapide

### Prérequis
*   **Java 17** ou supérieur
*   **Node.js** (v18+) & **NPM**
*   **MySQL**
*   **Maven**

### 1️⃣ Configuration & Lancement du Backend
1.  Créez une base de données MySQL nommée `digital_banking_db`.
2.  Vérifiez la configuration dans `backend/src/main/resources/application.properties`.
3.  Lancez le serveur :
    ```bash
    cd backend
    mvn spring-boot:run
    ```
    > API accessible sur : `http://localhost:8085`

### 2️⃣ Lancement du Frontend
1.  Installez les dépendances et lancez le serveur de développement :
    ```bash
    cd frontend
    npm install
    ng serve
    ```
    > Application accessible sur : `http://localhost:4200`

---

## 👥 Crédits

*   **Réalisé par :** Youssef Fellah
*   **Encadré par :** Pr. Mohamed Youssfi
