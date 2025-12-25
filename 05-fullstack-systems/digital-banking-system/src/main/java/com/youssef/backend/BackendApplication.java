package com.youssef.backend;

import com.youssef.backend.dtos.AccountOperationDTO;
import com.youssef.backend.dtos.CurrentAccountDTO;
import com.youssef.backend.dtos.CustomerDTO;
import com.youssef.backend.dtos.SavingAccountDTO;
import com.youssef.backend.enums.AccountStatus;
import com.youssef.backend.mappers.BankAccountMapper;
import com.youssef.backend.services.AccountOperationService;
import com.youssef.backend.services.BankAccountService;
import com.youssef.backend.services.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

import static com.youssef.backend.enums.OperationType.DEBIT;

/**
 * Point d'entrée de l'application Backend Digital Banking.
 * Cette classe configure et lance l'application Spring Boot.
 */
@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

    /**
     * Bean CommandLineRunner pour initialiser la base de données avec des données de test au démarrage.
     * Crée des clients, des comptes bancaires (courants et épargne) et des opérations.
     *
     * @param bankAccountMapper Mapper pour les comptes bancaires
     * @param customerService Service de gestion des clients
     * @param accountOperationService Service de gestion des opérations bancaires
     * @param bankAccountService Service de gestion des comptes bancaires
     * @return CommandLineRunner exécutable au démarrage
     */
    @Bean
    CommandLineRunner init(BankAccountMapper bankAccountMapper, CustomerService customerService, AccountOperationService accountOperationService, BankAccountService bankAccountService) {
        return args -> {
            // Création de clients fictifs
            Stream.of("customer1", "customer2", "customer3", "customer4", "customer5").forEach(name -> {
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setName(name);
                customerDTO.setEmail(name+"@gmail.com");
                customerService.saveCustomer(customerDTO);
            });

            // Pour chaque client, création d'un compte courant et d'un compte épargne
            customerService.listCustomers()
                    .forEach(customerDTO -> {
                CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
                SavingAccountDTO savingAccountDTO = new SavingAccountDTO();

                Date date = new Date();
                double balance = Math.random() * 12000;
                AccountStatus accountStatus = AccountStatus.CREATED;
                String currency = "MAD";

                // Configuration du compte courant
                currentAccountDTO.setCreationDate(date);
                currentAccountDTO.setId(UUID.randomUUID().toString());
                currentAccountDTO.setBalance(balance);
                currentAccountDTO.setAccountStatus(accountStatus);
                currentAccountDTO.setCurrency(currency);
                currentAccountDTO.setCustomerDTO(customerDTO);
                currentAccountDTO.setOverDraft(5000); // Découvert autorisé

                // Configuration du compte épargne
                savingAccountDTO.setCreationDate(date);
                savingAccountDTO.setId(UUID.randomUUID().toString());
                savingAccountDTO.setBalance(balance);
                savingAccountDTO.setAccountStatus(accountStatus);
                savingAccountDTO.setCurrency(currency);
                savingAccountDTO.setCustomerDTO(customerDTO);
                savingAccountDTO.setInterestRate(5.5); // Taux d'intérêt

                // Sauvegarde des comptes
                bankAccountService.saveCurrentAccount(currentAccountDTO);
                bankAccountService.saveSavingAccount(savingAccountDTO);
            });

            // Création d'opérations de débit pour chaque compte bancaire
            bankAccountService.listBankAccounts()
                    .forEach(bankAccountDTO -> {
                AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
                accountOperationDTO.setDate(new Date());
                accountOperationDTO.setAmount(1000);
                accountOperationDTO.setOperationType(DEBIT);
                accountOperationDTO.setBankAccountDTO(bankAccountDTO);

                accountOperationService.saveAccountOperation(accountOperationDTO);
            });

            // --- Affichage des données dans la console ---

            System.out.println("--- List of customers ---");
            customerService.listCustomers().forEach(customer ->{
                System.out.println("id => "+customer.getId()+"\nname => "+customer.getName()+"\nemail => "+customer.getEmail()+"\n");
            });

            System.out.println("--- List of bank accounts ---");
            bankAccountService.listBankAccounts().forEach(bankAccount -> {
                System.out.println("id => " + bankAccount.getId() + "\ncreationDate => " + bankAccount.getCreationDate() + "\nbalance => " + bankAccount.getBalance());
                System.out.println("costumer name => " + bankAccount.getCustomerDTO().getName());
            });

            System.out.println("--- List of operations ---");
                    accountOperationService.listAccountOperations()
                    .forEach(operation->{
                System.out.println("id => "+operation.getId()+"\ndate => "+operation.getDate()+"\namount => "+operation.getAmount());
            });
        };
    }
}
