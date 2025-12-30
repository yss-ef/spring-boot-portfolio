import {Customer} from './customer';

export interface Account {
  id: string;
  accountStatus: string;
  balance: number;
  creationDate: string; // Les dates JSON arrivent toujours en string
  currency: string;
  type: string;         // "SAV" ou "CUR"
  customerDTO: Customer;

  // Champs optionnels (?) car ils dépendent du type de compte
  interestRate?: number;
  overDraft?: number;

}
