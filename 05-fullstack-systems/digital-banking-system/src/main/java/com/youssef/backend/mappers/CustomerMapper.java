package com.youssef.backend.mappers;

import com.youssef.backend.dtos.CustomerRequestDTO;
import com.youssef.backend.dtos.CustomerResponseDTO;
import com.youssef.backend.entities.BankAccount;
import com.youssef.backend.entities.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomerMapper {

    public Customer fromRequest(CustomerRequestDTO customerRequestDTO){

        if (customerRequestDTO == null){return null;}

        Customer customer = new Customer();
        customer.setName(customerRequestDTO.getName());
        customer.setEmail(customerRequestDTO.getEmail());
        return customer;
    };

    public CustomerResponseDTO fromEntity(Customer customer){

        if (customer == null){return null;}

        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setId(customer.getId());
        customerResponseDTO.setName(customer.getName());
        customerResponseDTO.setEmail(customer.getEmail());

        customerResponseDTO.setBankAccountsId(new ArrayList<>());

        if (customer.getBankAccounts() != null){

            for(BankAccount bankAccount : customer.getBankAccounts()){
                customerResponseDTO.getBankAccountsId().add(bankAccount.getId());
            }
        }

        return customerResponseDTO;
    };
}
