package com.youssef.backend;

import com.youssef.backend.dtos.AccountOperationDTO;
import com.youssef.backend.dtos.CurrentAccountDTO;
import com.youssef.backend.dtos.CustomerDTO;
import com.youssef.backend.dtos.SavingAccountDTO;
import com.youssef.backend.enums.AccountStatus;
import com.youssef.backend.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

import static com.youssef.backend.enums.OperationType.DEBIT;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner init(
            CustomerService customerService,
            AccountOperationService accountOperationService,
            BankAccountService bankAccountService,
            AccountService accountService) {

        return args -> {
            // 1. Création des clients
            Stream.of("customer1", "customer2", "customer3", "customer4", "customer5").forEach(name -> {
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setName(name);
                customerDTO.setEmail(name + "@gmail.com");
                customerService.saveCustomer(customerDTO);
            });

            // 2. Création des comptes
            customerService.listCustomers().forEach(customerDTO -> {
                try {
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

                    bankAccountService.saveCurrentAccount(currentAccountDTO);
                    bankAccountService.saveSavingAccount(savingAccountDTO);
                } catch (Exception e) {
                    System.out.println("Erreur création compte pour " + customerDTO.getName());
                }
            });

            // 3. Création des opérations
            bankAccountService.listBankAccounts().forEach(bankAccountDTO -> {
                try {
                    AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
                    accountOperationDTO.setDate(new Date());
                    accountOperationDTO.setAmount(1000);
                    accountOperationDTO.setOperationType(DEBIT);
                    accountOperationDTO.setBankAccountDTO(bankAccountDTO);
                    accountOperationService.saveAccountOperation(accountOperationDTO);
                } catch (Exception e) {
                    System.out.println("Erreur création opération");
                }
            });

            // --- Affichage (Optionnel) ---
            System.out.println("--- List of operations ---");
            accountOperationService.listAccountOperations().forEach(operation -> {
                System.out.println("id => " + operation.getId() + " | amount => " + operation.getAmount());
            });

            // ==========================================
            // 4. INITIALISATION DE LA SÉCURITÉ (HORS DES BOUCLES !)
            // ==========================================

            // a. Création des Rôles
            try {
                accountService.addNewRole("USER");
                accountService.addNewRole("ADMIN");
            } catch (Exception e) {
                // Ignore si les rôles existent déjà
            }

            // b. Création des Utilisateurs et attribution des rôles
            try {
                accountService.addNewUser("user1", "1234", "user1@gmail.com", "1234");
                accountService.addRoleToUser("user1", "USER");
            } catch (Exception e) {
                System.out.println("User1 existe déjà.");
            }

            try {
                accountService.addNewUser("admin", "1234", "admin@gmail.com", "1234");
                accountService.addRoleToUser("admin", "USER");
                accountService.addRoleToUser("admin", "ADMIN");
            } catch (Exception e) {
                System.out.println("Admin existe déjà.");
            }

            System.out.println("✅ Initialisation terminée avec succès !");
        };
    }
}