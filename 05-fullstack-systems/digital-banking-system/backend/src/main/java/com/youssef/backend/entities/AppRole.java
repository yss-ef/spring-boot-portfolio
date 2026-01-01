package com.youssef.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppRole {
    @Id
    private String roleName; // Ex: "ADMIN", "USER"
}
