package com.youssef.backend.web;

import com.youssef.backend.dtos.CustomerDTO;
import com.youssef.backend.services.BankServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CustomerRestAPI {
    private BankServiceImpl bankServiceImpl;



    @GetMapping("/customers")
    public List<CustomerDTO> customers() {
        return bankServiceImpl.listCustomers();
    }
}
