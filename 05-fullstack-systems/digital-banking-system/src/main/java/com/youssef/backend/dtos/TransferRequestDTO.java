package com.youssef.backend.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO pour les demandes de virement (transfert).
 * Contient les comptes source et destination, le montant et la description.
 */
@Setter
@Getter
public class TransferRequestDTO {
    private String accountSource;
    private String accountDestination;
    private double amount;
    private String description;
}
