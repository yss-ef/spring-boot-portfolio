# 🏦 Digital Banking Backend

Bienvenue sur le projet **Digital Banking Backend**. Ce projet est une application robuste basée sur **Spring Boot** simulant un système bancaire numérique.

## 🏗 Architecture Globale

Le projet suit une architecture **N-Tiers** classique pour assurer la séparation des responsabilités et la maintenabilité :

```
src/main/java/com/youssef/backend
├── 📂 web          (Contrôleurs REST : Point d'entrée de l'API)
├── 📂 services     (Logique métier : Traitements, calculs, transactions)
├── 📂 entities     (Modèle de données : Mappage JPA avec la BDD)
├── 📂 security     (Configuration : JWT, Filtres, Encodeurs)
├── 📂 repositories (Accès aux données : Interfaces Spring Data JPA)
├── 📂 dtos         (Objets de transfert : Isolation des entités)
├── 📂 mappers      (Conversion : Entité <-> DTO)
└── 📂 bot          (Service Bot Telegram : Interaction utilisateur via Telegram)
```

---

## 📚 Analyse Détaillée par Couche

### 1️⃣ Couche de Données (JPA & Entities)

Cette couche gère la persistance des données et la structure de la base de données.

**La Logique :**
Nous utilisons la stratégie d'héritage **Single Table** pour gérer les comptes bancaires.
*   Nous avons une classe abstraite `BankAccount`.
*   Deux classes filles : `CurrentAccount` (Compte Courant) et `SavingAccount` (Compte Épargne).
*   Au lieu de créer plusieurs tables, JPA stocke tout dans une seule table `BankAccount` et utilise une colonne discriminante (`TYPE`) pour savoir de quel type de compte il s'agit.

**Code (`entities/BankAccount.java`) :**
```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Une seule table pour toute la hiérarchie
@DiscriminatorColumn(name = "TYPE", length = 4)       // Colonne qui distingue le type (ex: "CUR", "SAV")
public abstract class BankAccount {
    @Id
    private String id;
    private double balance;
    
    @ManyToOne
    private Customer customer; // Relation Many-to-One vers le client
    
    // ... getters et setters
}
```

---

### 2️⃣ Couche de Sécurité (Spring Security & JWT)

La sécurité est gérée de manière **Stateless** (sans session serveur) en utilisant des tokens **JWT (JSON Web Tokens)**.

**La Logique :**
1.  **Configuration** : Nous configurons une chaîne de filtres (`SecurityFilterChain`) pour intercepter les requêtes HTTP.
2.  **Stateless** : Nous désactivons les sessions HTTP classiques (`SessionCreationPolicy.STATELESS`). Chaque requête doit contenir le token.
3.  **JWT** : Nous utilisons un encodeur et un décodeur JWT pour signer et vérifier les tokens.

**Code de Configuration (`security/SecurityConfig.java`) :**
```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Pas de session en mémoire
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(ar -> ar
                    .requestMatchers("/auth/login/**").permitAll() // Endpoint de login public
                    .anyRequest().authenticated()                  // Tous les autres endpoints nécessitent une authentification
            )
            .oauth2ResourceServer(oa -> oa.jwt(Customizer.withDefaults())) // Active la gestion des tokens JWT
            .build();
}
```

**Code de Génération du Token (`web/SecurityRestController.java`) :**
```java
// Création des "Claims" (les informations contenues dans le token)
JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
        .subject(username)
        .claim("scope", scope) // Les rôles de l'utilisateur
        .expiresAt(Instant.now().plus(10, ChronoUnit.MINUTES)) // Expiration
        .build();

// Signature et encodage du token avec la clé secrète
String jwt = jwtEncoder.encode(JwtEncoderParameters.from(header, jwtClaimsSet)).getTokenValue();
```

---

### 3️⃣ Couche Métier (Services & Transactions)

C'est le cœur de l'application, où les règles de gestion sont appliquées.

**La Logique :**
Les opérations financières (comme un virement) doivent être **atomiques**. Cela signifie que tout doit réussir, ou tout doit échouer. Si on débite le compte A mais que le crédit du compte B échoue, l'argent ne doit pas disparaître. L'annotation `@Transactional` gère cela automatiquement (Rollback en cas d'erreur).

**Code (`services/AccountOperationServiceImpl.java`) :**
```java
@Transactional // Garantit l'intégrité des données
public void transfer(String source, String destination, double amount) {
    // 1. Retrait
    debit(source, amount, "Transfer to " + destination);
    // 2. Dépôt
    credit(destination, amount, "Transfer from " + source);
    // Si une erreur survient ici, le débit est annulé automatiquement.
}
```

---

### 4️⃣ Couche Web (Contrôleurs & DTOs)

Cette couche expose l'API REST au monde extérieur (Frontend, Mobile, etc.).

**La Logique :**
Nous appliquons le pattern **DTO (Data Transfer Object)**.
*   **Problème** : Les entités JPA (`Customer`, `BankAccount`) contiennent des relations bidirectionnelles qui peuvent causer des boucles infinies lors de la conversion en JSON. De plus, on ne veut pas toujours exposer toute la base de données.
*   **Solution** : Le Contrôleur reçoit et renvoie des objets simples (DTO). Un `Mapper` s'occupe de copier les données entre les Entités et les DTOs.

**Code du Mapper (`mappers/BankAccountMapper.java`) :**
```java
// Conversion Entité -> DTO
public CustomerDTO fromCustomer(Customer customer){
    CustomerDTO customerDTO = new CustomerDTO();
    BeanUtils.copyProperties(customer, customerDTO); // Copie intelligente des propriétés
    return customerDTO;
}
```

**Code du Contrôleur (`web/CustomerRestController.java`) :**
```java
@GetMapping("/")
public List<CustomerDTO> getAllCustomers() {
    // Le contrôleur appelle le service, qui lui renvoie des DTOs propres
    return customerService.listCustomers();
}
```

---

### 5️⃣ Couche Bot & IA (Telegram & OpenAI)

Cette couche permet l'interaction avec les utilisateurs via un bot Telegram intelligent.

**Fonctionnalités :**
*   **Liaison de compte** : Permet à un utilisateur Telegram de lier son compte bancaire via son email (`/link email@exemple.com`).
*   **Virements** : Exécution de virements bancaires via commande stricte (`/vir [Source] [Dest] [Montant]`).
*   **Assistant IA** : Utilisation de l'API OpenAI pour répondre aux questions en langage naturel sur le solde et l'historique des transactions.

**Code (`bot/TelegramBotService.java`) :**
```java
// Exemple de gestion de message
if (messageUser.startsWith("/vir")) {
    handleVirement(messageUser, telegramId, client);
} else {
    handleConversationIA(messageUser, telegramId, client);
}
```

**Code (`services/OpenAiService.java`) :**
```java
// Appel à l'API OpenAI
public String generateResponse(String userMessage) {
    // ... Construction de la requête JSON ...
    ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);
    return extractContent(response);
}
```

---

## 🚀 Installation et Démarrage

### Prérequis
*   Java 17+
*   MySQL
*   Maven

### Configuration (`application.properties`)
```properties
server.port=8085
spring.datasource.url=jdbc:mysql://localhost:3306/BANK?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create

# Configuration Telegram & OpenAI
telegram.bot.token=VOTRE_TOKEN_TELEGRAM
telegram.bot.username=VOTRE_BOT_USERNAME
openai.api.key=VOTRE_API_KEY_OPENAI
openai.model=gpt-3.5-turbo
openai.api.url=https://api.openai.com/v1/chat/completions
```

### Lancement
1.  Clonez le projet.
2.  Lancez : `mvn spring-boot:run`
3.  Accédez à : `http://localhost:8085`
4.  Données de test : Initialisées automatiquement au démarrage.

---

## 📡 Documentation de l'API

### 🔐 Authentification (`/auth`)
| Méthode | Endpoint | Description | Body Requis |
| :--- | :--- | :--- | :--- |
| `POST` | `/auth/login` | Authentification utilisateur | `{"username": "...", "password": "..."}` |
| `GET` | `/auth/profile` | Récupérer le profil connecté | *Aucun* (Token Bearer requis) |

### 👤 Clients (`/customers`)
| Méthode | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/customers/` | Liste tous les clients |
| `GET` | `/customers/{id}` | Récupère un client par son ID |
| `POST` | `/customers/` | Crée un nouveau client |
| `PATCH` | `/customers/{id}` | Met à jour partiellement un client |
| `DELETE` | `/customers/{id}` | Supprime un client |

### 🏦 Comptes Bancaires (`/accounts`)
| Méthode | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/accounts/` | Liste tous les comptes |
| `GET` | `/accounts/{id}` | Récupère un compte par son ID |
| `GET` | `/accounts/customer/{id}` | Liste les comptes d'un client spécifique |
| `POST` | `/accounts/current` | Crée un compte courant |
| `POST` | `/accounts/saving` | Crée un compte épargne |
| `PUT` | `/accounts/{id}` | Met à jour un compte |
| `DELETE` | `/accounts/{id}` | Supprime un compte |

### 💸 Opérations (`/accounts`)
| Méthode | Endpoint | Description | Body Requis |
| :--- | :--- | :--- | :--- |
| `GET` | `/accounts/{id}/operations` | Historique des opérations d'un compte | - |
| `POST` | `/accounts/debit` | Effectuer un débit | `{"accountId": "...", "amount": 100, "description": "..."}` |
| `POST` | `/accounts/credit` | Effectuer un crédit | `{"accountId": "...", "amount": 100, "description": "..."}` |
| `POST` | `/accounts/transfer` | Effectuer un virement | `{"accountSource": "...", "accountDestination": "...", "amount": 100}` |

---

## 🛠 Stack Technique
*   **Core :** Java, Spring Boot 3
*   **Data :** Spring Data JPA, Hibernate, MySQL
*   **Security :** Spring Security, OAuth2 Resource Server, Nimbus JOSE + JWT
*   **Bot & IA :** Telegram Bots API, OpenAI API (GPT-3.5)
*   **Utils :** Lombok, BeanUtils
