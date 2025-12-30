import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Account} from '../model/account';
import {Operation} from '../model/operation';
import {TransferRequest} from '../model/transfer-request';

@Injectable({
  providedIn: 'root',
})
export class AccountService {

  backendHost: string = "http://localhost:8085";

  constructor(private httpClient: HttpClient) {
  }

  public getAccounts() : Observable<Array<Account>>{
    return this.httpClient.get<Array<Account>>(this.backendHost+"/accounts");
  }

  getAccountsByCustomer(customerId: number) {
    return this.httpClient.get<Array<Account>>(this.backendHost+"/accounts/customer/" + customerId);
  }

  saveCurrentAccount(newAccount: Account) {
    return this.httpClient.post(this.backendHost+"/accounts/current", newAccount);
  }

  public saveSavingAccount(account: Account): Observable<Account> {
    return this.httpClient.post<Account>(this.backendHost + "/accounts/saving", account);
  }

  public getAccountOperations(accountId: string): Observable<Array<Operation>> {
    return this.httpClient.get<Array<Operation>>(this.backendHost + "/accounts/" + accountId + "/operations");
  }

  public transfer(transferRequest: TransferRequest): Observable<any> {
    return this.httpClient.post(this.backendHost + "/accounts/transfer", transferRequest);
  }
}
