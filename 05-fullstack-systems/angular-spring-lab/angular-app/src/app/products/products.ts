import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ProductService} from '../services/productService';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-products',
  imports: [CommonModule],
  templateUrl: './products.html',
  styleUrl: './products.css',
})
export class Products implements OnInit {
  products: any = [] ;
  // products!: Array<any>;
  // products: Array<any> = [];



  constructor(
    private productService:ProductService,
    private cd: ChangeDetectorRef,
    ) { // Injection des dependances avec le service productService.ts
  }
  ngOnInit () {
    //this.products =[
      //{id: 1, name : "computer", price : 2300, selected: true},
      //{id: 2, name : "printer", price : 1200, selected: false},
      //{id: 3, name : "smartphone", price : 1500, selected: true},
    //]
    this.getAllProducts()
  }

  getAllProducts() {
    this.productService.getAllProducts().subscribe({
      next: resp => {
        console.log("DONNÉES REÇUES DU BACKEND :", resp); // Debug : Verifier que les donnees sont recues
        this.products = resp;
        this.cd.detectChanges(); // Solution pour regler le probleme de detection
      },
      error: err => {
        console.log(err);
      }
    })
  }

  handleDelete(p: any) {
    let v = confirm("Are you sure you want to delete this product?");
    if (v) {
      //this.products = this.products.filter((product) => product.id != p.id);
      this.productService.deleteProduct(p).subscribe({
        next: resp => {
          console.log("DONNÉES SUPPIMEES DU BACKEND :", resp);
          this.getAllProducts(); // on re fetch la liste sous sa nouvelle forme
          this.cd.detectChanges();
        },
        error: err => {
          console.log(err);
        }
      }); // on filtre en oubliant le produit selectionne
    }
  }
}
