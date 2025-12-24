package com.youssef.backend;

import com.youssef.backend.dtos.AccountOperationDTO;
import com.youssef.backend.dtos.CurrentAccountDTO;
import com.youssef.backend.dtos.CustomerDTO;
import com.youssef.backend.dtos.SavingAccountDTO;
import com.youssef.backend.entities.AccountOperation;
import com.youssef.backend.entities.CurrentAccount;
import com.youssef.backend.entities.Customer;
import com.youssef.backend.entities.SavingAccount;
import com.youssef.backend.enums.AccountStatus;
import com.youssef.backend.mappers.BankAccountMapper;
import com.youssef.backend.repositories.AccountOperationRepository;
import com.youssef.backend.repositories.BankAccountRepository;
import com.youssef.backend.repositories.CustomerRepository;
import com.youssef.backend.services.BankServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

import static com.youssef.backend.enums.AccountStatus.CREATED;
import static com.youssef.backend.enums.OperationType.DEBIT;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

    @Bean
    CommandLineRunner init(BankServiceImpl bankServiceImpl, BankAccountMapper bankAccountMapper){
        return args -> {
            Stream.of("customer1", "customer2", "customer3", "customer4", "customer5").forEach(name -> {
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setId(UUID.randomUUID().toString());
                customerDTO.setName(name);
                customerDTO.setEmail(name+"@gmail.com");
                //customerRepository.save(customer);
                bankServiceImpl.saveCustomer(customerDTO);
            });

            //customerRepository.findAll()
            bankServiceImpl.listCustomers()
                    .forEach(customerDTO -> {
                CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
                SavingAccountDTO savingAccountDTO = new SavingAccountDTO();

                Date date = new Date();
                double balance = Math.random() * 12000;
                AccountStatus accountStatus = AccountStatus.CREATED;
                String currency = "MAD";

                currentAccountDTO.setCreationDate(date);
                currentAccountDTO.setId(UUID.randomUUID().toString());
                currentAccountDTO.setBalance(balance);
                currentAccountDTO.setAccountStatus(accountStatus);
                currentAccountDTO.setCurrency(currency);
                currentAccountDTO.setCustomerDTO(customerDTO);
                currentAccountDTO.setOverDraft(5000);


                savingAccountDTO.setCreationDate(date);
                savingAccountDTO.setId(UUID.randomUUID().toString());
                savingAccountDTO.setBalance(balance);
                savingAccountDTO.setAccountStatus(accountStatus);
                savingAccountDTO.setCurrency(currency);
                savingAccountDTO.setCustomerDTO(customerDTO);
                savingAccountDTO.setInterestRate(5.5);

                //bankAccountRepository.save(currentAccount);
                //bankAccountRepository.save(savingAccount);
                bankServiceImpl.saveCurrentAccount(currentAccountDTO);
                bankServiceImpl.saveSavingAccount(savingAccountDTO);
            });

            //bankAccountRepository.findAll()
            bankServiceImpl.listBankAccounts()
                    .forEach(bankAccountDTO -> {
                AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
                accountOperationDTO.setDate(new Date());
                accountOperationDTO.setAmount(1000);
                accountOperationDTO.setOperationType(DEBIT);
                accountOperationDTO.setBankAccountDTO(bankAccountDTO);

                bankServiceImpl.saveAccountOperation(accountOperationDTO);
            });

            // --- Affichage ---

            System.out.println("--- List of customers ---");
            bankServiceImpl.listCustomers().forEach(customer ->{
                System.out.println("id => "+customer.getId()+"\nname => "+customer.getName()+"\nemail => "+customer.getEmail()+"\n");
            });

            System.out.println("--- List of bank accounts ---");
            bankServiceImpl.listBankAccounts().forEach(bankAccount -> {
                System.out.println("id => " + bankAccount.getId() + "\ncreationDate => " + bankAccount.getCreationDate() + "\nbalance => " + bankAccount.getBalance());
                System.out.println("costumer name => " + bankAccount.getCustomerDTO().getName());
            });

            System.out.println("--- List of operations ---");
                    bankServiceImpl.listAccountOperations()
                    .forEach(operation->{
                System.out.println("id => "+operation.getId()+"\ndate => "+operation.getDate()+"\namount => "+operation.getAmount());
            });
        };
    }
}
