package com.youssef.backend.services;

import com.youssef.backend.dtos.CustomerDTO;
import com.youssef.backend.exeptions.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    List<CustomerDTO> listCustomers();
    CustomerDTO findCustomerById(Long id) throws CustomerNotFoundException;
    CustomerDTO patchCustomer(CustomerDTO customerDTO,Long id ) throws CustomerNotFoundException;
    void delCustomerById(Long id);
}
