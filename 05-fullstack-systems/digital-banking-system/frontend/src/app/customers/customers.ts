import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {CustomerService} from '../services/customer-service';
import {catchError, map, Observable, throwError} from 'rxjs';
import {Customer} from '../model/customer';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import {AuthService} from '../services/auth-service';

/**
 * Component for managing customers.
 * Allows searching, listing, and deleting customers.
 */
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

  constructor(private customerService: CustomerService, private formBuilder : FormBuilder, private router: Router, public authService: AuthService) { }

  /**
   * Initializes the component and the search form.
   * Loads the initial list of customers.
   */
  ngOnInit() {
    this.searchformGroup=this.formBuilder.group({
      keyword : this.formBuilder.control("")
    })
    this.handleSearchCustomers();
  }

  /**
   * Handles the search for customers based on the keyword entered in the form.
   */
  handleSearchCustomers() {
    let keyword=this.searchformGroup?.value.keyword;
    this.customers = this.customerService.searchCustomers(keyword).pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(err);
      })
    );
  }

  /**
   * Handles the deletion of a customer.
   * Asks for confirmation before deleting.
   * @param c The Customer object to be deleted.
   */
  handleCustomerDelete(c: Customer) {
    let conf : boolean = confirm("are you sure about that ?")
    if (conf){
      this.customerService.deleteCustomer(c).subscribe({
        next:data=>{
          // Refresh the list after deletion
          this.handleSearchCustomers();
        },
        error:err=>{
          console.log(err)
        }
      })
    }
  }
}
