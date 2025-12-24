package com.youssef.backend.web;

import com.youssef.backend.dtos.CustomerDTO;
import com.youssef.backend.exeptions.CustomerNotFoundException;
import com.youssef.backend.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CustomerRestAPI {
    private CustomerService customerService;

    // --- Mapping customer ---
    @GetMapping("/customers")
    public List<CustomerDTO> customers() {
        return customerService.listCustomers();
    }

    @GetMapping("/customers/{id}")
    public CustomerDTO customer(@PathVariable(name = "id") Long id) throws CustomerNotFoundException {
        return customerService.findCustomerById(id);
    }

    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO request){
        return customerService.saveCustomer(request);
    }

    @PatchMapping("/customers/{id}")
    public CustomerDTO patchCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable(name = "id") Long id) throws CustomerNotFoundException {
        return customerService.patchCustomer(customerDTO, id);
    }

    @DeleteMapping("/customers/{id}")
    public void delCustomer(@PathVariable(name = "id")Long id){
        customerService.delCustomerById(id);
    }
}
