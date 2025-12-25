# Digital Banking Backend

Ce projet est une application **Backend** basée sur **Spring Boot** pour la gestion d'une banque numérique. Elle expose une API RESTful permettant de gérer des clients, des comptes bancaires (Courants et Épargne) et d'effectuer des opérations financières (Virements, Débits, Crédits).

## 🚀 Fonctionnalités

*   **Gestion des Clients (Customers) :**
    *   Création, lecture, mise à jour et suppression de clients.
    *   Recherche de clients.
*   **Gestion des Comptes Bancaires (Bank Accounts) :**
    *   Gestion de deux types de comptes :
        *   **Compte Courant (Current Account) :** Avec autorisation de découvert (Overdraft).
        *   **Compte Épargne (Saving Account) :** Avec taux d'intérêt.
    *   Consultation du solde et de l'historique.
    *   Activation/Suspension de comptes.
*   **Opérations Bancaires :**
    *   **Débit :** Retrait d'argent d'un compte.
    *   **Crédit :** Dépôt d'argent sur un compte.
    *   **Virement (Transfer) :** Transfert d'argent d'un compte à un autre.
    *   Historique des opérations.

## 🛠 Technologies Utilisées

*   **Java** (JDK 17+)
*   **Spring Boot** (Framework principal)
*   **Spring Data JPA** (Couche d'accès aux données)
*   **MySQL** (Base de données relationnelle)
*   **Lombok** (Réduction du code boilerplate)
*   **Maven** (Gestion des dépendances)

## ⚙️ Configuration

Le fichier de configuration se trouve dans `src/main/resources/application.properties`.

```properties
server.port=8085
spring.datasource.url=jdbc:mysql://localhost:3306/BANK?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create
```

*   L'application tourne sur le port **8085**.
*   Elle se connecte à une base de données MySQL nommée **BANK**.
*   **Note :** La propriété `ddl-auto=create` recrée la base de données à chaque démarrage. Un `CommandLineRunner` est inclus pour initialiser des données de test (Clients, Comptes, Opérations) automatiquement.

## 📚 Documentation de l'API

Voici les principaux points de terminaison (Endpoints) disponibles :

### Clients (`/customers`)
| Méthode | Endpoint | Description |
| :--- | :--- | :--- |
| GET | `/customers/` | Liste tous les clients |
| GET | `/customers/{id}` | Récupère un client par son ID |
| POST | `/customers/` | Crée un nouveau client |
| PATCH | `/customers/{id}` | Met à jour un client |
| DELETE | `/customers/{id}` | Supprime un client |

### Comptes (`/accounts`)
| Méthode | Endpoint | Description |
| :--- | :--- | :--- |
| GET | `/accounts/` | Liste tous les comptes |
| GET | `/accounts/{id}` | Récupère un compte par son ID |
| GET | `/accounts/customer/{id}` | Liste les comptes d'un client |
| POST | `/accounts/current` | Crée un compte courant |
| POST | `/accounts/saving` | Crée un compte épargne |
| PUT | `/accounts/{id}` | Met à jour un compte |

### Opérations (`/accounts`)
| Méthode | Endpoint | Description |
| :--- | :--- | :--- |
| GET | `/accounts/{id}/operations` | Historique des opérations d'un compte |
| POST | `/accounts/debit` | Effectuer un débit |
| POST | `/accounts/credit` | Effectuer un crédit |
| POST | `/accounts/transfer` | Effectuer un virement |

## 🏗 Architecture

Le projet suit une architecture en couches classique :
1.  **Web Layer (Controllers) :** Gère les requêtes HTTP et les réponses JSON.
2.  **Service Layer :** Contient la logique métier (Validation de solde, règles de virement, etc.).
3.  **Data Access Layer (Repositories) :** Interfaces Spring Data JPA pour interagir avec la base de données.
4.  **Entities :** Classes persistantes mappées à la base de données.
5.  **DTOs (Data Transfer Objects) :** Objets utilisés pour transférer les données entre le client et le serveur, évitant d'exposer directement les entités.

## ▶️ Comment lancer l'application

1.  Assurez-vous d'avoir **MySQL** lancé.
2.  Clonez le dépôt.
3.  Ouvrez le projet dans votre IDE (IntelliJ IDEA, Eclipse, VS Code).
4.  Exécutez la classe principale `BackendApplication.java`.
5.  L'API sera accessible à l'adresse : `http://localhost:8085`.
