package com.youssef.backend.exeptions;

/**
 * Exception levée lorsqu'un compte bancaire n'est pas trouvé.
 */
public class BankAccountNotFoundException extends Exception {
    public BankAccountNotFoundException(String message) {
        super(message);
    }
}
