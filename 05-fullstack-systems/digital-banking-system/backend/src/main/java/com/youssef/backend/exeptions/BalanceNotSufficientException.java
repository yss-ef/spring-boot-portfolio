package com.youssef.backend.exeptions;

/**
 * Exception levée lorsque le solde d'un compte est insuffisant pour effectuer une opération.
 */
public class BalanceNotSufficientException extends Exception {
    public BalanceNotSufficientException(String message) {
        super(message);
    }
}
