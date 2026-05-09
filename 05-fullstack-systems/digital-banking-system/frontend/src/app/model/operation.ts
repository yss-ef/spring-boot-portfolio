import {Account} from './account';

/**
 * Represents a banking operation (transaction).
 */
export interface Operation{
  /** Unique identifier for the operation. */
  id:number,
  /** Date of the operation. */
  date:string,
  /** Amount involved in the operation. */
  amount:number,
  /** Type of operation (e.g., DEBIT, CREDIT). */
  type:string,
  /** The account associated with this operation. */
  account:Account,
  /** Description of the operation. */
  description:string,
}
