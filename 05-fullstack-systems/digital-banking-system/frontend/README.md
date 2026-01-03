# 🏦 Digital Banking Frontend

![Angular](https://img.shields.io/badge/Angular-17%2B-DD0031?style=for-the-badge&logo=angular&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-5.0-3178C6?style=for-the-badge&logo=typescript&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white)

Bienvenue sur le projet **Digital Banking Frontend**. Une interface utilisateur moderne, réactive et sécurisée, développée avec **Angular 17+**. Elle permet la gestion complète des clients, des comptes bancaires et des opérations financières en communiquant avec un backend Spring Boot.

---

## 📑 Table des Matières
1.  [Fonctionnalités Clés](#-fonctionnalités-clés)
2.  [Architecture du Projet](#-architecture-globale)
3.  [Analyse Technique](#-analyse-technique)
    *   [Composants (Standalone)](#1️⃣-composants-autonomes-standalone)
    *   [Services & API](#2️⃣-services--communication-api)
    *   [Sécurité (Interceptors)](#3️⃣-sécurité--intercepteurs)
    *   [Formulaires](#4️⃣-formulaires-réactifs)
4.  [Guide de Démarrage](#-installation-et-démarrage)
5.  [Structure de l'Application](#-structure-de-lapplication)
6.  [Stack Technique](#-stack-technique)

---

## ✨ Fonctionnalités Clés

*   **🔐 Authentification & Sécurité** : Connexion sécurisée via **JWT** et gestion de session (LocalStorage).
*   **👥 Gestion des Clients** : Recherche en temps réel, ajout, modification et suppression de clients avec validation stricte.
*   **🏦 Gestion des Comptes** : Support des comptes **Courants** (avec découvert) et **Épargne** (avec taux d'intérêt).
*   **💸 Opérations Bancaires** : Consultation de l'historique des transactions et exécution de virements compte-à-compte.

---

## 🏗 Architecture Globale

Le projet adopte une architecture **Angular Moderne** favorisant la modularité, la maintenabilité et la performance.

```mermaid
graph TD;
    User[Utilisateur] --> View[Composants (UI)];
    View --> Service[Services (Logique Métier)];
    Service --> Interceptor[Intercepteur HTTP (Token JWT)];
    Interceptor --> API[Backend API (Spring Boot)];
```

### Structure du Code
```
src/app
├── 📂 services     # Services API (Auth, Accounts, Customers)
├── 📂 model        # Interfaces et Types (DTOs)
├── 📂 interceptor  # Gestion du Token JWT
├── 📂 customers    # Composants de gestion des clients
├── 📂 accounts     # Composants de gestion des comptes
├── 📂 operations   # Composants de gestion des opérations
└── 📂 login        # Page d'authentification
```

---

## 📚 Analyse Technique

### 1️⃣ Composants Autonomes (Standalone)
Utilisation exclusive des **Standalone Components** pour réduire la complexité (suppression des `NgModule`) et optimiser le chargement.

```typescript
@Component({
  selector: 'app-customers',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './customers.html',
  styleUrl: './customers.css',
})
export class Customers implements OnInit { ... }
```

### 2️⃣ Services & Communication API
Isolation de la logique métier et des appels HTTP dans des services dédiés, injectés dans les composants. Utilisation de **RxJS** pour la gestion asynchrone.

### 3️⃣ Sécurité & Intercepteurs
Un `HttpInterceptor` intercepte toutes les requêtes sortantes pour y injecter automatiquement le token d'authentification `Authorization: Bearer ...`.

### 4️⃣ Formulaires Réactifs
Gestion des formulaires via **Reactive Forms** pour une validation robuste et découplée du template HTML.

---

## 🚀 Installation et Démarrage

### Prérequis
*   **Node.js** (v18 ou supérieur)
*   **Backend Digital Banking** lancé sur le port `8085`

### 1. Installation des dépendances
```bash
npm install
```

### 2. Lancement du serveur de développement
```bash
ng serve
```
L'application sera accessible sur `http://localhost:4200/`.

---

## 📱 Structure de l'Application

### 🔐 Authentification
*   **Route** : `/login`
*   **Description** : Formulaire de connexion pour récupérer le JWT.

### 👤 Clients (`/customers`)
*   **Route** : `/customers`
*   **Fonctions** : Liste des clients, barre de recherche, boutons d'actions (Edit/Delete).

### 🏦 Comptes & Opérations (`/accounts`)
*   **Route** : `/accounts`
*   **Fonctions** : Consultation d'un compte par ID, affichage du solde, historique des opérations, et formulaire de virement.

---

## 🛠 Stack Technique

| Catégorie | Technologie | Usage |
| :--- | :--- | :--- |
| **Core** | Angular 17+ | Framework Frontend |
| **Langage** | TypeScript 5.0 | Typage et Logique |
| **UI/UX** | Bootstrap 5 | Design et Responsivité |
| **Data** | RxJS | Programmation Réactive |
| **Build** | Angular CLI / Vite | Outils de build |

---

## 🧪 Tests
Pour lancer les tests unitaires :
```bash
ng test
```

---

## 👥 Crédits

*   **Réalisé par :** Youssef Fellah
*   **Encadré par :** Pr. Mohamed Youssfi
