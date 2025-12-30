# Digital Banking Frontend

Ceci est l'application frontend pour le système de Banque Digitale, construite avec **Angular 17+**. Elle fournit une interface utilisateur robuste pour gérer les clients de la banque, les comptes et les opérations, en utilisant des pratiques de développement web modernes.

## 🚀 Fonctionnalités

*   **Authentification & Sécurité**
    *   Connexion utilisateur avec authentification JWT (JSON Web Token).
    *   **Intercepteur :** Ajout automatique du token aux requêtes HTTP.
    *   Déconnexion sécurisée et gestion du LocalStorage.
    *   Contrôle d'accès basé sur les rôles (adaptation de l'interface utilisateur selon les rôles).

*   **Gestion des Clients**
    *   **Recherche :** Filtrage des clients en temps réel.
    *   **Opérations CRUD :** Créer, Lire, Mettre à jour et Supprimer des clients.
    *   **Validation :** Validation stricte des formulaires pour l'intégrité des données.

*   **Gestion des Comptes**
    *   **Polymorphisme :** Gestion de différents types de comptes (Courant vs Épargne) avec des règles métier spécifiques.
    *   **Vues Dynamiques :** Affichage des comptes spécifiques à un client ou listes globales.

*   **Opérations (Transactions)**
    *   **Historique :** Vue chronologique des débits et crédits.
    *   **Virements :** Transferts de fonds sécurisés entre comptes avec validation.

---

## 💻 Plongée Technique & Aperçu du Code

Ce projet suit l'architecture **Angular Moderne**. Voici les choix techniques clés et les détails d'implémentation :

### 1. Composants Autonomes (Standalone Components)
Nous nous sommes éloignés de l'approche traditionnelle `NgModule`. Tous les composants sont **Standalone**, rendant l'application plus légère et plus facile à tester.

**Exemple (`src/app/customers/customers.ts`) :**
```typescript
@Component({
  selector: 'app-customers',
  standalone: true, // Implicite dans Angular 17+ si 'imports' est utilisé
  imports: [CommonModule, ReactiveFormsModule, RouterLink], // Imports directs
  templateUrl: './customers.html',
  styleUrl: './customers.css',
})
export class Customers implements OnInit { ... }
```

### 2. Formulaires Réactifs (Reactive Forms)
Nous utilisons les **Reactive Forms** pour toutes les saisies de données. Cela offre une meilleure scalabilité, réutilisabilité et testabilité par rapport aux formulaires pilotés par template. La logique de validation est définie dans le code TypeScript, gardant le HTML propre.

**Exemple (`src/app/new-customer/new-customer.ts`) :**
```typescript
this.newCustomerFormGroup = this.formBuilder.group({
  name : this.formBuilder.control("", [Validators.required, Validators.minLength(4)]),
  email : this.formBuilder.control("", [Validators.required, Validators.email]),
});
```

### 3. Intercepteur HTTP & Sécurité JWT
La sécurité est gérée de manière centralisée. Au lieu d'ajouter l'en-tête Authorization à chaque appel de service manuellement, nous utilisons un **Intercepteur HTTP**.

**Comment ça marche :**
1.  L'intercepteur intercepte *chaque* requête HTTP sortante.
2.  Il vérifie si un token JWT existe dans le `localStorage`.
3.  Il clone la requête et ajoute l'en-tête `Authorization: Bearer <token>`.
4.  Il transmet la requête.

**Extrait de Code (`src/app/interceptor/app-http-interceptor.ts`) :**
```typescript
intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
  if (!request.url.includes("/auth/login")) {
    let token = localStorage.getItem('access-token');
    if (token) {
      let newRequest = request.clone({
        headers: request.headers.set('Authorization', 'Bearer ' + token)
      });
      return next.handle(newRequest);
    }
  }
  return next.handle(request);
}
```

### 4. Modèle Service-Repository
Toute la logique HTTP est encapsulée dans des **Services** (`src/app/services/`). Les composants ne font jamais d'appels HTTP directs ; ils s'abonnent aux Observables fournis par les services. Cela assure la séparation des préoccupations.

**Exemple (`src/app/services/account-service.ts`) :**
```typescript
public getAccounts(): Observable<Array<Account>> {
  return this.httpClient.get<Array<Account>>(this.backendHost + "/accounts");
}
```

### 5. Pipe Async & Gestion des Observables
Dans de nombreuses vues, nous utilisons le `AsyncPipe` (`| async`) dans le template HTML. Cela s'abonne automatiquement à l'Observable lorsque le composant se charge et se désabonne lorsqu'il est détruit, évitant les fuites de mémoire.

**Exemple (`src/app/customers/customers.html`) :**
```html
<ng-container *ngIf="customers | async as listCustomer; else failure">
   <!-- Les données sont disponibles dans la variable 'listCustomer' -->
   <tr *ngFor="let c of listCustomer">...</tr>
</ng-container>
```

---

## 🛠 Technologies Utilisées

*   **Framework :** [Angular](https://angular.io/) (v17+)
*   **Langage :** TypeScript
*   **Style :** [Bootstrap 5](https://getbootstrap.com/) & [Bootstrap Icons](https://icons.getbootstrap.com/)
*   **Gestion d'État :** RxJS (Observables, Subjects)
*   **Outil de Build :** Angular CLI / Vite

## 📋 Prérequis

*   **Node.js** (v18+)
*   **Angular CLI** (`npm install -g @angular/cli`)
*   **Backend :** Une instance en cours d'exécution du Backend Digital Banking sur le port `8085`.

## ⚙️ Installation & Lancement

1.  **Installer les dépendances :**
    ```bash
    npm install
    ```

2.  **Démarrer l'application :**
    ```bash
    ng serve
    ```
    Naviguez vers `http://localhost:4200/`.

## 🏗 Structure du Projet

*   `src/app/services/` : Logique de communication API.
*   `src/app/model/` : Interfaces TypeScript (DTOs).
*   `src/app/interceptor/` : Logique de sécurité (JWT).
*   `src/app/guards/` : Protection des routes.
*   `src/app/customers/`, `src/app/accounts/` : Modules fonctionnels.
