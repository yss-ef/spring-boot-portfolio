# Angular frontend application

This document details the implementation of the client-side application
developed with Angular. This application serves as the frontend for a
full-stack product management project.

## Application role

The Angular application provides the user interface for interacting with the
system. It enables users to view a list of products and perform deletions.
The application retrieves and modifies data through calls to a REST API
provided by the Spring Boot backend.

## Technical stack

- **Framework:** Angular
- **Communication:** `HttpClientModule` for REST API calls.
- **Styling:** Bootstrap and Bootstrap Icons.
- **Dependency management:** npm

## Implementation details

The frontend architecture is modular, utilizing components and services for
separation of concerns.

### Data service

The `ProductService` centralizes communication with the backend API. It exposes
methods for retrieving and deleting products, making components independent of
data access logic.

```typescript
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

### Display component

The main component for product management uses the `ProductService` to fetch
data and handle user actions.

```typescript
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
        this.cd.detectChanges();
      },
      error: err => console.log(err)
    });
  }

  handleDelete(p: any) {
    if (confirm("Are you sure you want to delete this product?")) {
      this.productService.deleteProduct(p).subscribe({
        next: () => this.getAllProducts(),
        error: err => console.log(err)
      });
    }
  }
}
```

### Template

The view uses Angular's modern syntax, such as `@for` and `@if`, to display the
product list and action buttons.

```html
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

## Getting started

### 1. Install dependencies

```bash
npm install
```

### 2. Launch the development server

```bash
ng serve
```

Access the application at `http://localhost:4200/`.

Note: Ensure the backend server is running at `http://localhost:8080`.

## Credits

Developed by Youssef Fellah for the Engineering Cycle at Mundiapolis
University.
