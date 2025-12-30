import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

import { AccountService } from '../services/account-service';
import { Account } from '../model/account'; // Assurez-vous d'avoir ce modèle
import { TransferRequest } from '../model/transfer-request';

@Component({
  selector: 'app-new-transfer',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule
  ],
  templateUrl: './new-operation.html',
  styleUrl: './new-operation.css',
})
export class NewOperation implements OnInit {

  transferFormGroup!: FormGroup;
  accounts!: Observable<Array<Account>>; // Variable pour stocker la liste des comptes

  constructor(
    private fb: FormBuilder,
    private accountService: AccountService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // 1. Charger la liste des comptes existants
    this.accounts = this.accountService.getAccounts();

    // 2. Initialiser le formulaire
    this.transferFormGroup = this.fb.group({
      accountSource: ['', [Validators.required]],
      accountDestination: ['', [Validators.required]],
      amount: [0, [Validators.required, Validators.min(1)]]
    });
  }

  handleTransfer() {
    if (this.transferFormGroup.invalid) return;

    let transferRequest: TransferRequest = this.transferFormGroup.value;

    // Sécurité : Empêcher le virement vers le même compte
    if (transferRequest.accountSource === transferRequest.accountDestination) {
      alert("Source and Destination accounts cannot be the same!");
      return;
    }

    this.accountService.transfer(transferRequest).subscribe({
      next: (data) => {
        alert("Transfer successful!");
        this.router.navigateByUrl("/accounts");
      },
      error: (err) => {
        console.log(err);
        alert(err.message);
      }
    });
  }
}
