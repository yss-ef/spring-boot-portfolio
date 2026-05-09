import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Account} from '../model/account';
import {Operation} from '../model/operation';
import {TransferRequest} from '../model/transfer-request';

/**
 * Service responsible for managing bank accounts.
 * Provides methods to fetch accounts, save new accounts, retrieve operations, and perform transfers.
 */
@Injectable({
  providedIn: 'root',
})
export class AccountService {

  backendHost: string = "http://localhost:8085";

  constructor(private httpClient: HttpClient) {
  }

  /**
   * Retrieves the list of all bank accounts.
   * @returns An Observable containing an array of Account objects.
   */
  public getAccounts() : Observable<Array<Account>>{
    return this.httpClient.get<Array<Account>>(this.backendHost+"/accounts");
  }

  /**
   * Retrieves accounts belonging to a specific customer.
   * @param customerId The unique identifier of the customer.
   * @returns An Observable containing an array of Account objects for the specified customer.
   */
  getAccountsByCustomer(customerId: number) {
    return this.httpClient.get<Array<Account>>(this.backendHost+"/accounts/customer/" + customerId);
  }

  /**
   * Saves a new current account.
   * @param newAccount The Account object representing the new current account.
   * @returns An Observable of the saved Account.
   */
  saveCurrentAccount(newAccount: Account) {
    return this.httpClient.post(this.backendHost+"/accounts/current", newAccount);
  }

  /**
   * Saves a new saving account.
   * @param account The Account object representing the new saving account.
   * @returns An Observable of the saved Account.
   */
  public saveSavingAccount(account: Account): Observable<Account> {
    return this.httpClient.post<Account>(this.backendHost + "/accounts/saving", account);
  }

  /**
   * Retrieves the operations (transactions) for a specific account.
   * @param accountId The unique identifier of the account.
   * @returns An Observable containing an array of Operation objects.
   */
  public getAccountOperations(accountId: string): Observable<Array<Operation>> {
    return this.httpClient.get<Array<Operation>>(this.backendHost + "/accounts/" + accountId + "/operations");
  }

  /**
   * Performs a fund transfer between two accounts.
   * @param transferRequest The TransferRequest object containing source, destination, and amount.
   * @returns An Observable of the operation result.
   */
  public transfer(transferRequest: TransferRequest): Observable<any> {
    return this.httpClient.post(this.backendHost + "/accounts/transfer", transferRequest);
  }
}
