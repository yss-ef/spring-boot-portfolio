package com.youssef.backend.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class CustomerResponseDTO {
    private String id;
    private String name;
    private String email;
    private List<String> bankAccountsId;
}
