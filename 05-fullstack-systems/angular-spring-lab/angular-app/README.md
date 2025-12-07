# Application Frontend (Angular)

Ce document détaille l'implémentation de l'application cliente, développée avec Angular. Elle constitue la partie frontend du projet full-stack de gestion de produits.

## 1. Rôle de l'Application

L'application Angular fournit l'interface utilisateur (UI) pour interagir avec le système. Elle permet de visualiser la liste des produits et de les supprimer. Toutes les données sont récupérées et modifiées via des appels à une API REST fournie par le backend Spring Boot.

## 2. Technologies Utilisées

*   **Framework** : Angular
*   **Communication HTTP** : `HttpClientModule` pour les appels à l'API REST.
*   **Style** : Bootstrap et Bootstrap Icons pour l'interface utilisateur.
*   **Gestion des dépendances** : npm

## 3. Détails de l'Implémentation

L'architecture frontend est modulaire, basée sur des composants et des services.

### Service de Données (`ProductService.ts`)

Ce service centralise la communication avec l'API backend. Il expose des méthodes pour chaque opération (récupérer, supprimer les produits), rendant les composants indépendants de la logique d'accès aux données.

```typescript
// src/app/services/productService.ts
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

### Composant d'Affichage (`products.ts`)

Le composant principal pour l'affichage et la gestion des produits. Il utilise le `ProductService` pour récupérer les données et gérer les actions de l'utilisateur.

```typescript
// src/app/products/products.ts
@Component({ /* ... */ })
export class Products implements OnInit {
  products: any = [] ;

  constructor(
    private productService: ProductService,
    private cd: ChangeDetectorRef,
  ) {}

  ngOnInit () {
    this.getAllProducts();
  }

  getAllProducts() {
    this.productService.getAllProducts().subscribe({
      next: resp => {
        this.products = resp;
        this.cd.detectChanges(); // Forcer la mise à jour de la vue
      },
      error: err => console.log(err)
    });
  }

  handleDelete(p: any) {
    if (confirm("Are you sure you want to delete this product?")) {
      this.productService.deleteProduct(p).subscribe({
        next: () => this.getAllProducts(), // Recharger la liste
        error: err => console.log(err)
      });
    }
  }
}
```

### Template (`products.html`)

La vue utilise la syntaxe moderne d'Angular (`@for`, `@if`) pour afficher la liste des produits et les boutons d'action.

```html
<!-- src/app/products/products.html -->
<table class="table">
  <thead>
    <tr>
      <th>Id</th>
      <th>Name</th>
      <th>Price</th>
      <th>Selected</th>
    </tr>
  </thead>
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

## 4. Pour commencer

1.  **Installer les dépendances :**
    ```bash
    npm install
    ```
2.  **Lancer le serveur de développement :**
    ```bash
    ng serve
    ```
3.  Ouvrez votre navigateur et accédez à `http://localhost:4200/`.

**Note :** Assurez-vous que le serveur backend est en cours d'exécution sur `http://localhost:8080`.
