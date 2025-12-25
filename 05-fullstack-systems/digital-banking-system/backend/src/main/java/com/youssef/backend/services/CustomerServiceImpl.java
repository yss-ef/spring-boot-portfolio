package com.youssef.backend.services;

import com.youssef.backend.dtos.CustomerDTO;
import com.youssef.backend.entities.Customer;
import com.youssef.backend.exeptions.CustomerNotFoundException;
import com.youssef.backend.mappers.BankAccountMapper;
import com.youssef.backend.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implémentation du service de gestion des clients.
 * Utilise CustomerRepository pour l'accès aux données et BankAccountMapper pour la conversion DTO/Entité.
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;
    BankAccountMapper bankAccountMapper;


    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = bankAccountMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        log.info("Customer saved successfully");
        return   bankAccountMapper.fromCustomer(savedCustomer);
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers = customerRepository.findAll();
        log.info("Customers found successfully");
        return customers.stream().map(customer -> bankAccountMapper
                        .fromCustomer(customer))
                .toList();
    }

    @Override
    public CustomerDTO findCustomerById(Long id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(()->new CustomerNotFoundException("Customer not found"));
        log.info("Customer found successfully");
        return bankAccountMapper.fromCustomer(customer);
    }

    @Override
    public CustomerDTO patchCustomer(CustomerDTO customerDTO,Long id ) throws CustomerNotFoundException {
        Customer foundCustomer = customerRepository.findById(id).orElseThrow(()->new CustomerNotFoundException("Customer not found"));
        Customer requestCustomer = bankAccountMapper.fromCustomerDTO(customerDTO);
        if (requestCustomer.getEmail() != null) {
            foundCustomer.setEmail(requestCustomer.getEmail());
        }
        if (requestCustomer.getName() != null) {
            foundCustomer.setName(requestCustomer.getName());
        }
        log.info("Customer patched successfully");
        Customer savedCustomer = customerRepository.save(foundCustomer);
        return  bankAccountMapper.fromCustomer(savedCustomer);
    }

    @Override
    public void delCustomerById(Long id){
        customerRepository.deleteById(id);
        log.info("Customer deleted successfully");
    }

}
