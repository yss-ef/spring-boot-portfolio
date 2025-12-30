import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {CustomerService} from '../services/customer-service';
import {catchError, map, Observable, throwError} from 'rxjs';
import {Customer} from '../model/customer';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';

@Component({
  selector: 'app-customers',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './customers.html',
  styleUrl: './customers.css',
})

export class Customers implements OnInit {
  customers!: Observable<Array<Customer>>;
  errorMessage! : string;
  searchformGroup! : FormGroup;

  constructor(private customerService: CustomerService, private formBuilder : FormBuilder, private router: Router) { }

  ngOnInit() {
    this.searchformGroup=this.formBuilder.group({
      keyword : this.formBuilder.control("")
    })
    this.handleSearchCustomers();
  }

  handleSearchCustomers() {
    let keyword=this.searchformGroup?.value.keyword;
    this.customers = this.customerService.searchCustomers(keyword).pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(err);
      })
    );
  }

  handleCustomerDelete(c: Customer) {
    let conf : boolean = confirm("are you sure about that ?")
    if (conf){
      this.customerService.deleteCustomer(c).subscribe({
        next:data=>{
          this.router.navigateByUrl("/customers");
        },
        error:err=>{
          console.log(err)
        }
      })
    }
  }
}
