import {RouterModule, Routes} from '@angular/router';
import {Customers} from './customers/customers';
import {Accounts} from './accounts/accounts';
import {NgModule} from '@angular/core';
import {NewCustomer} from './new-customer/new-customer';
import {EditCustomer} from './edit-customer/edit-customer';
import {NewCurrentAccount} from './new-current-account/new-current-account';
import {NewSavingAccount} from './new-saving-account/new-saving-account';
import {OperationsComponent} from './operations/operations';
import {NewOperation} from './new-operation/new-operation';
import {Home} from './home/home';
import {Login} from './login/login';

export const routes: Routes = [
  { path : "customers", component : Customers },
  { path : "accounts", component : Accounts},
  { path : "new-customer", component : NewCustomer},
  { path : "edit-customer/:id", component : EditCustomer},
  { path : "accounts/:id", component : Accounts},
  { path : "new-current-account", component : NewCurrentAccount },
  { path : "new-saving-account", component : NewSavingAccount},
  { path: 'operations/:id', component: OperationsComponent },
  { path : "new-operation", component: NewOperation},
  { path : "home", component: Home },
  { path: "login", component: Login},
  { path: '', redirectTo: '/login', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {}
