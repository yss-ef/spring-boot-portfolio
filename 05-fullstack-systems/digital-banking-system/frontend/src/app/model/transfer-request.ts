/**
 * Represents a request to transfer funds between accounts.
 */
export interface TransferRequest {
  /** The ID of the source account. */
  accountSource: string;
  /** The ID of the destination account. */
  accountDestination: string;
  /** The amount to transfer. */
  amount: number
}
