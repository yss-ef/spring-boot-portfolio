import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Observable, catchError, throwError } from 'rxjs';

import { Customer } from '../model/customer';
import { CustomerService } from '../services/customer-service';
import { AccountService } from '../services/account-service';
import { Account } from '../model/account'; // Import de votre interface

@Component({
  selector: 'app-new-current-account',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule
  ],
  templateUrl: './new-current-account.html',
  styleUrl: './new-current-account.css',
})
export class NewCurrentAccount implements OnInit {

  protected newCurrentAccountFormGroup!: FormGroup;
  protected customers!: Observable<Array<Customer>>;
  protected errorMessage!: string;

  constructor(
    private fb: FormBuilder,
    private accountService: AccountService,
    private customerService: CustomerService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // 1. On récupère les clients
    this.customers = this.customerService.getAllCustomers().pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(err);
      })
    );

    // 2. On initialise le formulaire
    this.newCurrentAccountFormGroup = this.fb.group({
      customerId: [null, [Validators.required]],
      balance: [0, [Validators.required, Validators.min(100)]],
      currency: ['MAD', [Validators.required]],
      accountStatus: ['CREATED', [Validators.required]],
      overDraft: [5000, [Validators.required, Validators.min(0)]]
    });
  }

  protected handleSaveCurrentAccount() {
    if (this.newCurrentAccountFormGroup.invalid) return;

    let formValue = this.newCurrentAccountFormGroup.value;

    let newAccount: Account = {
      id: '',
      creationDate: '',
      balance: formValue.balance,
      currency: formValue.currency,
      accountStatus: formValue.accountStatus,
      overDraft: formValue.overDraft,
      type: 'CurrentAccount',

      customerDTO: {
        id: formValue.customerId,
        name: '',
        email: ''
      }
    };

    this.accountService.saveCurrentAccount(newAccount).subscribe({
      next: data => {
        alert("Current Account saved successfully!");
        this.router.navigateByUrl("/accounts");
      },
      error: err => {
        console.error(err);
        alert("Error: " + err.message);
      }
    });
  }
}
