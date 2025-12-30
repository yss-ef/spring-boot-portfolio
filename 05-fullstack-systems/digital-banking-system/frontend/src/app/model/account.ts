import {Customer} from './customer';

/**
 * Represents a bank account.
 */
export interface Account {
  /** Unique identifier for the account. */
  id: string;
  /** Status of the account (e.g., ACTIVATED, SUSPENDED). */
  accountStatus: string;
  /** Current balance of the account. */
  balance: number;
  /** Date when the account was created (as a string). */
  creationDate: string; // Les dates JSON arrivent toujours en string
  /** Currency of the account (e.g., EUR, USD). */
  currency: string;
  /** Type of the account ("SAV" for Saving, "CUR" for Current). */
  type: string;         // "SAV" ou "CUR"
  /** The customer associated with this account. */
  customerDTO: Customer;

  // Champs optionnels (?) car ils dépendent du type de compte
  /** Interest rate (only for Saving Accounts). */
  interestRate?: number;
  /** Overdraft limit (only for Current Accounts). */
  overDraft?: number;

}
