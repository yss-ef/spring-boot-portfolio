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
import {AdminDashboard} from './admin-dashboard/admin-dashboard';
import {authGuard} from './guards/auth-guard';

/**
 * Application routes configuration.
 * Defines the mapping between URL paths and components.
 */
export const routes: Routes = [
  { path : "customers", component : Customers, canActivate: [authGuard]},
  { path : "accounts", component : Accounts, canActivate: [authGuard]},
  { path : "new-customer", component : NewCustomer, canActivate: [authGuard]},
  { path : "edit-customer/:id", component : EditCustomer, canActivate: [authGuard]},
  { path : "accounts/:id", component : Accounts, canActivate: [authGuard]},
  { path : "new-current-account", component : NewCurrentAccount, canActivate: [authGuard]},
  { path : "new-saving-account", component : NewSavingAccount, canActivate: [authGuard]},
  { path: 'operations/:id', component: OperationsComponent, canActivate: [authGuard]},
  { path : "new-operation", component: NewOperation, canActivate: [authGuard]},
  { path : "home", component: Home, canActivate: [authGuard]},
  { path: "login", component: Login},
  { path: "admin", component: AdminDashboard, canActivate: [authGuard]},
  { path: '', redirectTo: '/login', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})

export class AppRoutingModule {}
