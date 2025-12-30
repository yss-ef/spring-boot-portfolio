import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router'; // 1. Import nécessaire
import {CustomerService} from '../services/customer-service';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-edit-customer',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './edit-customer.html',
  styleUrl: './edit-customer.css',
})
export class EditCustomer implements OnInit {

  patchCustomerFormeGroup!: FormGroup;
  customerId!: number;

  constructor(private formBuilder: FormBuilder,
              private customerService: CustomerService,
              private route: ActivatedRoute, // 2. Pour lire l'URL
              private router: Router) {      // 3. Pour la redirection après save
  }

  ngOnInit() {
    // 4. On récupère l'ID depuis l'URL
    this.customerId = this.route.snapshot.params['id'];

    // 5. On initialise le formulaire vide d'abord
    this.patchCustomerFormeGroup = this.formBuilder.group({
      id : this.formBuilder.control({value: '', disabled: true}), // ID non modifiable
      name : this.formBuilder.control('', [Validators.required, Validators.minLength(4)]),
      email : this.formBuilder.control('', [Validators.required, Validators.email]),
    });

    // 6. On appelle le service pour récupérer les infos du client
    this.customerService.getCustomer(this.customerId).subscribe({
      next: (customer) => {
        // 7. On remplit le formulaire avec les données reçues
        this.patchCustomerFormeGroup.patchValue({
          id: customer.id,
          name: customer.name,
          email: customer.email
        });
      },
      error: (err) => {
        console.log(err);
      }
    });
  }

  handlePatchCustomer() {
    let customer = this.patchCustomerFormeGroup.value;
    // Comme le champ ID est 'disabled', il n'est pas inclus dans .value
    // On le rajoute manuellement pour être sûr
    customer.id = this.customerId;

    this.customerService.patchCustomer(customer).subscribe({
      next: data => {
        alert("Customer has been successfully updated!");
        // Redirection vers la liste des clients après succès
        this.router.navigateByUrl("/customers");
      },
      error: error => {
        console.log(error);
      }
    });
  }
}
