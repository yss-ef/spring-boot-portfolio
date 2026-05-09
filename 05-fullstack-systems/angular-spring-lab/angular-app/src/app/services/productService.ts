import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
    //products =[
    //  {id: 1, name : "computer", price : 2300, selected: true},
    //  {id: 2, name : "printer", price : 1200, selected: false},
    //  {id: 3, name : "smartphone", price : 1500, selected: true},
    //]

  constructor(private http: HttpClient) {}

  getAllProducts() {
      //return this.products;
      return this.http.get("http://localhost:8080/api/products");
  }

  deleteProduct(p: any) {
      //this.products = this.products.filter((product) => product.id != p.id);
      return this.http.delete("http://localhost:8080/api/products/" + p.id);
  }
}
