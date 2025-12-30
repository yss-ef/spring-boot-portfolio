# 🏦 Digital Banking Project

Bienvenue sur le dépôt global du projet **Digital Banking**. Ce projet est une solution complète de banque numérique, composée d'un backend robuste basé sur Spring Boot et d'un frontend moderne développé avec Angular.

## 📂 Structure du Projet

Le projet est divisé en deux modules principaux :

*   **`backend/`** : L'API RESTful et la logique métier (Java/Spring Boot).
*   **`frontend/`** : L'interface utilisateur web (Angular).

---

## 🏗 Architecture Globale

Le système suit une architecture **client-serveur** classique où le frontend Angular communique avec le backend Spring Boot via une API REST sécurisée par JWT.

### 🔙 Backend (Spring Boot)
Le backend gère la persistance des données, la sécurité et la logique métier complexe (virements atomiques, gestion des comptes, etc.).
*   **Technologies** : Java 17, Spring Boot 3, Spring Data JPA, Spring Security, MySQL.
*   **Points Clés** : Architecture N-Tiers, Sécurité Stateless (JWT), Transactions Atomiques.
*   [Voir le README du Backend pour plus de détails](./backend/README.md)

### 🖥️ Frontend (Angular)
Le frontend offre une expérience utilisateur fluide et réactive pour gérer les clients et les comptes.
*   **Technologies** : Angular 17+, TypeScript, Bootstrap 5, RxJS.
*   **Points Clés** : Composants Standalone, Reactive Forms, Intercepteurs HTTP pour la sécurité.
*   [Voir le README du Frontend pour plus de détails](./frontend/README.md)

---

## 🚀 Démarrage Rapide

Pour lancer l'application complète, vous devez démarrer le backend et le frontend séparément.

### 1️⃣ Démarrer le Backend
1.  Assurez-vous d'avoir **Java 17+** et **MySQL** installés.
2.  Configurez la base de données dans `backend/src/main/resources/application.properties`.
3.  Dans le dossier `backend/` :
    ```bash
    mvn spring-boot:run
    ```
    Le serveur démarrera sur `http://localhost:8085`.

### 2️⃣ Démarrer le Frontend
1.  Assurez-vous d'avoir **Node.js** et **Angular CLI** installés.
2.  Dans le dossier `frontend/` :
    ```bash
    npm install
    ng serve
    ```
    L'application sera accessible sur `http://localhost:4200`.

---

## 🔐 Fonctionnalités Principales

*   **Authentification** : Système de login sécurisé avec JWT.
*   **Gestion des Clients** : Ajout, modification, suppression et recherche de clients.
*   **Comptes Bancaires** : Gestion des comptes courants et épargne (polymorphisme).
*   **Opérations** : Dépôts, retraits et virements compte à compte.
*   **Historique** : Consultation des transactions passées.

---

## 👥 Crédits

*   **Réalisé par :** Youssef Fellah
*   **Encadré par :** Pr. Mohamed Youssfi
