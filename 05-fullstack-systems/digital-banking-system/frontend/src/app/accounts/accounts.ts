import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {catchError, Observable, throwError} from 'rxjs';
import {AccountService} from '../services/account-service';
import {Account} from '../model/account';
import {ActivatedRoute, RouterLink} from '@angular/router';

@Component({
  selector: 'app-accounts',
  imports: [
    CommonModule,
    RouterLink
  ],
  templateUrl: './accounts.html',
  styleUrl: './accounts.css',
})
export class Accounts implements OnInit {
  accounts! : Observable<Array<Account>>;
  customerId! : number;
  errorMessage : string | undefined;
  constructor(private accountService: AccountService, private route: ActivatedRoute,) { }

  ngOnInit() {
    this.customerId = this.route.snapshot.params['id'];

    if(this.customerId) {
      this.accounts = this.accountService.getAccountsByCustomer(this.customerId).pipe(
        catchError(err => {
          this.errorMessage = err.message;
          return throwError(err);
        })
      );
    } else {
      this.accounts = this.accountService.getAccounts().pipe(
        catchError(err => {
          this.errorMessage = err.message;
          return throwError(err);
        })
      );
    }
  }
}
