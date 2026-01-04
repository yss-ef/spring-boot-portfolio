# 🏦 Digital Banking Frontend

![Angular](https://img.shields.io/badge/Angular-17%2B-DD0031?style=for-the-badge&logo=angular&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-5.0-3178C6?style=for-the-badge&logo=typescript&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white)
![Chart.js](https://img.shields.io/badge/Chart.js-4.x-FF6384?style=for-the-badge&logo=chartdotjs&logoColor=white)

Bienvenue sur le projet **Digital Banking Frontend**. Une interface utilisateur moderne, réactive et sécurisée, développée avec **Angular 17+**. Elle permet la gestion complète des clients, des comptes bancaires et des opérations financières en communiquant avec un backend Spring Boot.

---

## 📑 Table des Matières
1.  [Fonctionnalités Clés](#-fonctionnalités-clés)
2.  [Architecture du Projet](#-architecture-globale)
3.  [Analyse Technique](#-analyse-technique)
4.  [Guide de Démarrage](#-installation-et-démarrage)
5.  [Structure de l'Application](#-structure-de-lapplication)
6.  [Stack Technique](#-stack-technique)

---

## ✨ Fonctionnalités Clés

*   **🔐 Authentification & Sécurité** : Connexion sécurisée via **JWT**, gestion des rôles (Admin/User) et protection des routes.
*   **📊 Tableau de Bord Admin** : Vue d'ensemble avec KPIs (Total clients, comptes, actifs) et graphiques sur la répartition des comptes et des soldes.
*   **👥 Gestion des Clients** : Recherche en temps réel, ajout, modification et suppression de clients (CRUD complet).
*   **🏦 Gestion des Comptes** : Support des comptes **Courants** (avec découvert) et **Épargne** (avec taux d'intérêt).
*   **💸 Opérations Bancaires** : Consultation de l'historique des transactions et exécution de virements compte-à-compte.

---

## 🏗 Architecture Globale

Le projet adopte une architecture **Angular Moderne** favorisant la modularité, la maintenabilité et la performance.

### Structure du Code
```
src/app
├── 📂 admin-dashboard # Composant du tableau de bord
├── 📂 services        # Services API (Auth, Accounts, Customers)
├── 📂 model           # Interfaces et Types (DTOs)
├── 📂 interceptor     # Gestion du Token JWT
├── 📂 customers       # Composants de gestion des clients
├── 📂 accounts        # Composants de gestion des comptes
├── 📂 operations      # Composants de gestion des opérations
└── 📂 login           # Page d'authentification
```

---

## 📚 Analyse Technique

### 1️⃣ Tableau de Bord (Chart.js)
Le dashboard administrateur utilise **Chart.js** pour visualiser les données de l'application. Il présente :
*   Des **KPIs** (Key Performance Indicators) pour un aperçu rapide.
*   Un **graphique Doughnut** pour la répartition des types de comptes.
*   Un **graphique en barres** pour comparer les actifs totaux par type de compte.

### 2️⃣ Sécurité Basée sur les Rôles
L'interface s'adapte en fonction des rôles de l'utilisateur (extraits du JWT) :
*   Le lien vers le **Dashboard** n'est visible que pour les `ADMIN`.
*   Les boutons de **suppression/modification** sont masqués pour les utilisateurs non-`ADMIN`.

### 3️⃣ Composants Autonomes (Standalone)
Utilisation exclusive des **Standalone Components** pour réduire la complexité (suppression des `NgModule`) et optimiser le chargement.

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

### 📊 Dashboard Admin
*   **Route** : `/admin`
*   **Accès** : Réservé aux administrateurs.
*   **Fonctions** : Visualisation des statistiques globales de la banque.

### 👤 Clients (`/customers`)
*   **Route** : `/customers`
*   **Fonctions** : Liste des clients, recherche, et actions de gestion (CRUD).

### 🏦 Comptes & Opérations (`/accounts`)
*   **Route** : `/accounts`
*   **Fonctions** : Consultation des comptes, affichage du solde, et historique des opérations.

---

## 🛠 Stack Technique

| Catégorie | Technologie | Usage |
| :--- | :--- | :--- |
| **Core** | Angular 17+ | Framework Frontend |
| **Langage** | TypeScript 5.0 | Typage et Logique |
| **UI/UX** | Bootstrap 5 | Design et Responsivité |
| **Data Visualization** | Chart.js | Graphiques du Dashboard |
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
