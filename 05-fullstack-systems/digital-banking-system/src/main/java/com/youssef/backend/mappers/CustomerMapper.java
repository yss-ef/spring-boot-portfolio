package com.youssef.backend.mappers;

import com.youssef.backend.dtos.CustomerRequestDTO;
import com.youssef.backend.dtos.CustomerResponseDTO;
import com.youssef.backend.entities.BankAccount;
import com.youssef.backend.entities.Customer;

public class CustomerMapper {

    public Customer fromRequest(CustomerRequestDTO customerRequestDTO){
        Customer customer = new Customer();
        customer.setName(customerRequestDTO.getName());
        customer.setEmail(customerRequestDTO.getEmail());
        return customer;
    };

    public CustomerResponseDTO fromEntity(Customer customer){
        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setId(customer.getId());
        customerResponseDTO.setName(customer.getName());
        customerResponseDTO.setEmail(customer.getEmail());
        for(BankAccount bankAccount : customer.getBankAccounts()){
            customerResponseDTO.getBankAccountsId().add(bankAccount.getId());
        }
        return customerResponseDTO;
    };
}
