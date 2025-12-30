import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ActivatedRoute, RouterLink} from '@angular/router';
import { Observable, catchError, throwError } from 'rxjs';
import { AccountService } from '../services/account-service';
import { Operation } from '../model/operation';

@Component({
  selector: 'app-account-operations',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './operations.html',
  styleUrl: './operations.css'
})
export class OperationsComponent implements OnInit {

  accountId!: string;
  operations!: Observable<Array<Operation>>;
  errorMessage!: string;

  constructor(private route: ActivatedRoute, private accountService: AccountService) {}

  ngOnInit(): void {
    this.accountId = this.route.snapshot.params['id'];

    this.operations = this.accountService.getAccountOperations(this.accountId).pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(err);
      })
    );
  }
}
