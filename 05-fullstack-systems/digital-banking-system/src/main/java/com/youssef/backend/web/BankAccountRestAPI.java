package com.youssef.backend.web;

import com.youssef.backend.dtos.BankAccountDTO;
import com.youssef.backend.dtos.CurrentAccountDTO;
import com.youssef.backend.dtos.SavingAccountDTO;
import com.youssef.backend.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class BankAccountRestAPI {

    BankAccountService bankAccountService;

    @GetMapping("/accounts")
    public List<BankAccountDTO> bankAccounts(){
        return bankAccountService.listBankAccounts();
    }

    @GetMapping("/accounts/customer={id}")
    public List<BankAccountDTO> bankAccountsByCustomer(@PathVariable(name = "id") Long id){
        return bankAccountService.listBankAccountsByCustomerId(id);
    }

    @PostMapping("/accounts/current")
    public CurrentAccountDTO saveCurrentAccount(@RequestBody CurrentAccountDTO currentAccountDTO){
        return bankAccountService.saveCurrentAccount(currentAccountDTO);
    }

    @PostMapping("/accounts/saving")
    public SavingAccountDTO saveSavingAccount(@RequestBody SavingAccountDTO savingAccountDTO){
        return bankAccountService.saveSavingAccount(savingAccountDTO);
    }

    // to do : patch et delete d'un compte

}
