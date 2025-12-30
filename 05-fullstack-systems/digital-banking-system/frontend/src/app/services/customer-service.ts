import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Customer} from '../model/customer';

/**
 * Service responsible for managing customers.
 * Provides methods to search, save, and delete customers.
 */
@Injectable({
  providedIn: 'root',
})
export class CustomerService {

  backendHost: string = "http://localhost:8085";

  constructor(private httpClient : HttpClient) { }

  /**
   * Searches for customers based on a keyword.
   * @param keyword The search keyword (e.g., name).
   * @returns An Observable containing an array of matching Customer objects.
   */
  public searchCustomers(keyword: string): Observable<Array<Customer>> {
    return this.httpClient.get<Array<Customer>>(this.backendHost+"/customers/search?keyword=" + keyword);
  }

  /**
   * Saves a new customer.
   * @param customer The Customer object to be saved.
   * @returns An Observable of the saved Customer.
   */
  public saveCustomer(customer: Customer): Observable<Customer> {
    return this.httpClient.post<Customer>(this.backendHost+"/customers", customer);
  }

  /**
   * Deletes a customer.
   * @param customer The Customer object to be deleted.
   * @returns An Observable of the deletion result.
   */
  public deleteCustomer(customer: Customer){
    return this.httpClient.delete(this.backendHost + "/customers/" + customer.id);
  }

  /**
   * Retrieves a single customer by ID.
   * @param id The ID of the customer.
   * @returns An Observable of the Customer object.
   */
  public getCustomer(id: number): Observable<Customer> {
    return this.httpClient.get<Customer>(this.backendHost + "/customers/" + id);
  }

  /**
   * Updates an existing customer.
   * @param customer The Customer object with updated information.
   * @returns An Observable of the updated Customer.
   */
  public patchCustomer(customer: Customer): Observable<Customer> {
    return this.httpClient.put<Customer>(this.backendHost + "/customers/" + customer.id, customer);
  }
}
