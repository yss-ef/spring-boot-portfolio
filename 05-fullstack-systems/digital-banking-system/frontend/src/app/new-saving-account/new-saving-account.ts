import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Observable, catchError, throwError } from 'rxjs';

import { Customer } from '../model/customer';
import { CustomerService } from '../services/customer-service';
import { AccountService } from '../services/account-service';
import { Account } from '../model/account';

@Component({
  selector: 'app-new-saving-account',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule
  ],
  templateUrl: './new-saving-account.html',
  styleUrl: './new-saving-account.css',
})
export class NewSavingAccount implements OnInit {

  protected newSavingAccountFormGroup!: FormGroup;
  protected customers!: Observable<Array<Customer>>;
  protected errorMessage!: string;

  constructor(
    private fb: FormBuilder,
    private accountService: AccountService,
    private customerService: CustomerService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // 1. Récupération des clients
    this.customers = this.customerService.getAllCustomers().pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(err);
      })
    );

    // 2. Initialisation du formulaire (Avec Interest Rate au lieu de OverDraft)
    this.newSavingAccountFormGroup = this.fb.group({
      customerId: [null, [Validators.required]],
      balance: [0, [Validators.required, Validators.min(100)]],
      currency: ['MAD', [Validators.required]],
      accountStatus: ['CREATED', [Validators.required]],
      interestRate: [3.5, [Validators.required, Validators.min(0), Validators.max(100)]]
    });
  }

  protected handleSaveSavingAccount() {
    if (this.newSavingAccountFormGroup.invalid) return;

    let formValue = this.newSavingAccountFormGroup.value;

    // 3. Construction de l'objet Account
    let newAccount: Account = {
      id: '',
      creationDate: '',
      balance: formValue.balance,
      currency: formValue.currency,
      accountStatus: formValue.accountStatus,

      // Spécifique Saving Account
      interestRate: formValue.interestRate,
      // overDraft: undefined, // Pas de découvert ici
      type: 'SavingAccount', // Discriminator pour le backend

      customerDTO: {
        id: formValue.customerId,
        name: '',
        email: ''
      }
    };

    // 4. Appel au service (Notez le changement de méthode)
    this.accountService.saveSavingAccount(newAccount).subscribe({
      next: data => {
        alert("Saving Account created successfully!");
        this.router.navigateByUrl("/accounts");
      },
      error: err => {
        console.error(err);
        alert("Error: " + err.message);
      }
    });
  }
}
