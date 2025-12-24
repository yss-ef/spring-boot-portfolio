package com.youssef.backend.dtos;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class CustomerDTO {
    private String id;
    private String name;
    private String email;
}
