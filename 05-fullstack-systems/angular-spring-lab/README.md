# Compte Rendu du TP 4 : Application Full-Stack avec Angular et Spring Boot

Ce document est le rapport du quatrième travail pratique, centré sur le développement d'une application web full-stack avec une architecture découplée. L'objectif était de construire une interface utilisateur réactive avec Angular qui communique avec une API REST développée avec Spring Boot pour la gestion de produits.

## 1. Architecture de l'Application

L'application est conçue selon une architecture client-serveur moderne :

*   **Backend (Serveur)** : Une API REST développée avec **Spring Boot**. Elle gère la logique métier, l'accès aux données et expose des endpoints pour les opérations CRUD (Create, Read, Update, Delete).
*   **Frontend (Client)** : Une application monopage (SPA) développée avec **Angular**. Elle consomme l'API du backend pour afficher les données et permettre les interactions utilisateur.

Cette séparation claire des responsabilités permet un développement et un déploiement indépendants des deux parties de l'application.

## 2. Technologies Utilisées

**Backend (dossier `backend-app`)**
*   **Framework** : Spring Boot
*   **Accès aux données** : Spring Data JPA
*   **API** : Spring Web (pour l'API REST)
*   **Base de données** : H2 (base de données en mémoire)
*   **Utilitaires** : Lombok
*   **Build Tool** : Maven

**Frontend (dossier `angular-app`)**
*   **Framework** : Angular
*   **Communication HTTP** : `HttpClientModule` d'Angular
*   **Style** : Bootstrap, Bootstrap Icons
*   **Gestion des dépendances** : npm

## 3. Détails de l'Implémentation

### Partie Backend (Spring Boot)

L'architecture du backend est divisée en plusieurs couches logiques : entité, repository, service et contrôleur.

#### Couche de Persistance (JPA)

**Entité `Product.java`**
La classe `Product` représente les données des produits. Elle utilise des annotations JPA pour le mapping objet-relationnel et des annotations de validation pour l'intégrité des données.

```java
// C:/Users/Youssef/OneDrive/Documents/Youssef/TPs/Tp JEE/Tp 4 - Angular/backend/backend-app/src/main/java/com/youssef/backendapp/entities/Product.java
@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;
    @Min(0)
    private double price;
    private Boolean selected;
}
```

**Repository `ProductRepository.java`**
Une interface simple qui étend `JpaRepository` pour fournir toutes les opérations CRUD standard sans code supplémentaire.

```java
// C:/Users/Youssef/OneDrive/Documents/Youssef/TPs/Tp JEE/Tp 4 - Angular/backend/backend-app/src/main/java/com/youssef/backendapp/repositories/ProductRepository.java
public interface ProductRepository extends JpaRepository<Product,Long> {
}
```

#### Couche Web (API REST)

**Contrôleur `ProductRestAPI.java`**
Ce contrôleur expose les endpoints REST pour la gestion des produits. Il est configuré pour accepter les requêtes de n'importe quelle origine (`@CrossOrigin("*")`), ce qui est essentiel pour la communication avec l'application Angular.

```java
// C:/Users/Youssef/OneDrive/Documents/Youssef/TPs/Tp JEE/Tp 4 - Angular/backend/backend-app/src/main/java/com/youssef/backendapp/web/ProductRestAPI.java
@RestController
@CrossOrigin("*")
@RequestMapping("/api")
@AllArgsConstructor
public class ProductRestAPI {
    private ProductService productService;

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return productService.findAll();
    }

    @DeleteMapping("/products/{id}")
    public void deleteProductById(@PathVariable(name = "id") Long id){
        productService.deleteById(id);
    }
    // ... autres endpoints
}
```

#### Initialisation des Données

La classe `BackendAppApplication.java` utilise un `CommandLineRunner` pour peupler la base de données H2 avec des données de test au démarrage de l'application.

```java
// C:/Users/Youssef/OneDrive/Documents/Youssef/TPs/Tp JEE/Tp 4 - Angular/backend/backend-app/src/main/java/com/youssef/backendapp/BackendAppApplication.java
@Bean
CommandLineRunner init(ProductService productService) {
    return args -> {
        productService.addProduct(new Product(null, "ordinateur", 5000, true ));
        productService.addProduct(new Product(null, "telephone", 2500, true ));
        productService.addProduct(new Product(null, "tablette", 3000, false ));
        // ...
    };
}
```

### Partie Frontend (Angular)

L'architecture du frontend est basée sur des composants et des services pour une bonne séparation des préoccupations.

#### Service de Données

**`ProductService.ts`**
Ce service encapsule la communication avec l'API backend. Il utilise le `HttpClient` d'Angular pour effectuer les requêtes HTTP.

```typescript
// C:/Users/Youssef/OneDrive/Documents/Youssef/TPs/Tp JEE/Tp 4 - Angular/angular-app/src/app/services/productService.ts
@Injectable({
  providedIn: 'root',
})
export class ProductService {
  constructor(private http: HttpClient) {}

  getAllProducts() {
      return this.http.get("http://localhost:8080/api/products");
  }

  deleteProduct(p: any) {
      return this.http.delete("http://localhost:8080/api/products/" + p.id);
  }
}
```

#### Composant d'Affichage

**`products.ts`**
Le composant `Products` injecte le `ProductService` pour récupérer et afficher la liste des produits. Il gère également l'action de suppression.

```typescript
// C:/Users/Youssef/OneDrive/Documents/Youssef/TPs/Tp JEE/Tp 4 - Angular/angular-app/src/app/products/products.ts
@Component({ /* ... */ })
export class Products implements OnInit {
  products: any = [] ;

  constructor(
    private productService:ProductService,
    private cd: ChangeDetectorRef,
  ) {}

  ngOnInit () {
    this.getAllProducts();
  }

  getAllProducts() {
    this.productService.getAllProducts().subscribe({
      next: resp => {
        this.products = resp;
        this.cd.detectChanges(); // Forcer la détection de changement
      },
      // ...
    });
  }

  handleDelete(p: any) {
    this.productService.deleteProduct(p).subscribe({
      next: resp => {
        this.getAllProducts(); // Recharger la liste après suppression
      },
      // ...
    });
  }
}
```

**Template `products.html`**
Le template utilise la nouvelle syntaxe de contrôle de flux (`@for`, `@if`) d'Angular pour afficher les produits de manière performante.

```html
<!-- C:/Users/Youssef/OneDrive/Documents/Youssef/TPs/Tp JEE/Tp 4 - Angular/angular-app/src/app/products/products.html -->
<table class="table">
  <!-- ... thead ... -->
  @if (products) {
    <tbody>
      @for (p of products; track p){
        <tr>
          <td>{{p.id}}</td>
          <td>{{p.name}}</td>
          <td>{{p.price}}</td>
          <td>
            @if (p.selected) { <i class="bi bi-check-circle"></i> }
            @else { <i class="bi bi-circle"></i> }
          </td>
          <td>
            <button (click)="handleDelete(p)" class="btn btn-outline-danger">
              <i class="bi bi-trash"></i>
            </button>
          </td>
        </tr>
      }
    </tbody>
  }
</table>
```

## 4. Configuration de la Base de Données

Le backend est configuré pour utiliser une base de données H2 en mémoire, ce qui facilite le développement et les tests sans nécessiter de serveur de base de données externe.

Fichier `application.properties` :
```properties
# C:/Users/Youssef/OneDrive/Documents/Youssef/TPs/Tp JEE/Tp 4 - Angular/backend/backend-app/src/main/resources/application.properties
spring.datasource.url=jdbc:h2:mem:product-db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# Activer la console H2 pour le débogage
spring.h2.console.enabled=true

# Stratégie de génération du schéma (create pour recréer la DB à chaque redémarrage)
spring.jpa.hibernate.ddl-auto=create
```
La console H2 est accessible à l'adresse `http://localhost:8080/h2-console` pour inspecter les données.

## 5. Difficultés Rencontrées et Solutions

*   **Problème de Communication Frontend-Backend (CORS)** : Lors des premiers appels de l'application Angular vers l'API Spring Boot, des erreurs de type "Cross-Origin Resource Sharing" (CORS) sont apparues. Le navigateur bloque par défaut les requêtes HTTP entre différentes origines (`localhost:4200` et `localhost:8080`).
    *   **Solution** : L'annotation `@CrossOrigin("*")` a été ajoutée au contrôleur REST dans le backend. Cela indique au navigateur que l'API est autorisée à recevoir des requêtes de n'importe quelle origine, résolvant ainsi le problème.

*   **Problème de Mise à Jour de la Vue dans Angular** : Après avoir récupéré les produits depuis le backend, la vue ne se mettait pas toujours à jour automatiquement pour afficher les données.
    *   **Solution** : Le `ChangeDetectorRef` d'Angular a été injecté dans le composant `Products`, et sa méthode `detectChanges()` a été appelée manuellement après la réception des données. Cela force Angular à relancer son cycle de détection de changements et à rafraîchir l'interface utilisateur.

## 6. Conclusion

Ce TP a été une excellente introduction au développement d'applications full-stack modernes. Il a permis de mettre en pratique les concepts suivants :

*   La création d'une API REST robuste et bien structurée avec Spring Boot.
*   Le développement d'une interface utilisateur dynamique et réactive avec Angular.
*   La gestion de la communication asynchrone entre un client et un serveur.
*   Le débogage de problèmes courants dans un environnement full-stack, notamment les erreurs CORS et les subtilités de la détection de changements dans les frameworks frontend.

Le découplage entre le frontend et le backend offre une grande flexibilité et constitue une base solide pour la construction d'applications web complexes et évolutives.
