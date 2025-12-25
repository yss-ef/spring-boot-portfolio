package com.youssef.backend.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * Data Transfer Object (DTO) pour l'entité Customer.
 * Utilisé pour transférer les données des clients entre les couches de l'application.
 */
@Service
@Getter
@Setter
public class CustomerDTO {
    private Long id;
    private String name;
    private String email;
}
