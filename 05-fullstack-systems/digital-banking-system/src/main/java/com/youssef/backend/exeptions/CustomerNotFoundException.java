package com.youssef.backend.exeptions;

/**
 * Exception levée lorsqu'un client n'est pas trouvé dans la base de données.
 */
public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
