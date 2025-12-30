import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Customer} from '../model/customer';
import {CustomerService} from '../services/customer-service';
import {Router} from '@angular/router';

/**
 * Component for creating a new customer.
 */
@Component({
  selector: 'app-new-customer',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './new-customer.html',
  styleUrl: './new-customer.css',
})
export class NewCustomer implements OnInit {

  newCustomerFormeGroup! : FormGroup;

  constructor(private formBuilder : FormBuilder, private customerService: CustomerService, private router: Router) {
  }

  /**
   * Initializes the form for creating a new customer with validation rules.
   */
  ngOnInit() {
    this.newCustomerFormeGroup=this.formBuilder.group({
      name : this.formBuilder.control("", [Validators.required, Validators.minLength(4)]),
      email : this.formBuilder.control("", [Validators.required, Validators.email]),
    })
  }

  /**
   * Handles the submission of the new customer form.
   * Saves the customer and redirects to the customers list upon success.
   */
  protected handleSaveCustomer() {
    let customer:Customer=this.newCustomerFormeGroup.value;
    this.customerService.saveCustomer(customer).subscribe({
      next: data=>{
        alert("Customer has been successfully saved!");
        this.router.navigateByUrl("/customers");
      },
      error: error=>{
        alert(error.message);
      }
    });
  }
}
