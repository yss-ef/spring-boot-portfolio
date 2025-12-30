import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Customer} from '../model/customer';

@Injectable({
  providedIn: 'root',
})
export class CustomerService {

  backendHost: string = "http://localhost:8085";

  constructor(private httpClient : HttpClient) { }

  public searchCustomers(keyword: string): Observable<Array<Customer>> {
    return this.httpClient.get<Array<Customer>>(this.backendHost+"/customers/search?keyword=" + keyword);
  }

  public saveCustomer(customer: Customer): Observable<Customer> {
    return this.httpClient.post<Customer>(this.backendHost+"/customers", customer);
  }

  public deleteCustomer(customer: Customer){
    return this.httpClient.delete(this.backendHost + "/customers/" + customer.id);
  }

  public getCustomer(id: number) {
    return this.httpClient.get<Customer>(this.backendHost + "/customers/" + id);
  }

  patchCustomer(customer: Customer) {
    return this.httpClient.patch(this.backendHost+"/customers/" + customer.id, customer);
  }

  getAllCustomers() {
    return this.httpClient.get<Array<Customer>>(this.backendHost+"/customers");
  }
}
