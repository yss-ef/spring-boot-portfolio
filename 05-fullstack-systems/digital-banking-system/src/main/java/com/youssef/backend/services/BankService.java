package com.youssef.backend.services;

import com.youssef.backend.dtos.CustomerRequestDTO;
import com.youssef.backend.entities.Customer;

public interface BankService {
    public CustomerRequestDTO saveCustomer(CustomerRequestDTO customerRequest);
}
