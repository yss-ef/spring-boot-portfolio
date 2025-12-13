package com.youssef.backend;

import com.youssef.backend.entities.AccountOperation;
import com.youssef.backend.entities.CurrentAccount;
import com.youssef.backend.entities.Customer;
import com.youssef.backend.entities.SavingAccount;
import com.youssef.backend.enums.AccountStatus;
import com.youssef.backend.repositories.AccountOperationRepository;
import com.youssef.backend.repositories.BankAccountRepository;
import com.youssef.backend.repositories.CustomerRepository;
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
    CommandLineRunner init(
            CustomerRepository customerRepository,
            BankAccountRepository bankAccountRepository,
            AccountOperationRepository accountOperationRepository
    ){
        return args -> {
            Stream.of("customer1", "customer2", "customer3", "customer4", "customer5").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });

            customerRepository.findAll().forEach(customer -> {
                CurrentAccount currentAccount = new CurrentAccount();
                SavingAccount savingAccount = new SavingAccount();

                Date date = new Date();
                double balance = Math.random() * 12000;
                AccountStatus accountStatus = AccountStatus.CREATED;
                String currency = "MAD";

                currentAccount.setCreationDate(date);
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(balance);
                currentAccount.setAccountStatus(accountStatus);
                currentAccount.setCurrency(currency);
                currentAccount.setCustomer(customer);
                currentAccount.setOverDraft(5000);


                savingAccount.setCreationDate(date);
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(balance);
                savingAccount.setAccountStatus(accountStatus);
                savingAccount.setCurrency(currency);
                savingAccount.setCustomer(customer);
                savingAccount.setInterestRate(5.5);

                bankAccountRepository.save(currentAccount);
                bankAccountRepository.save(savingAccount);
            });

            bankAccountRepository.findAll().forEach(bankAccount -> {
                AccountOperation accountOperation = new AccountOperation();
                accountOperation.setDate(new Date());
                accountOperation.setAmount(1000);
                accountOperation.setOperationType(DEBIT);
                accountOperation.setBankAccount(bankAccount);

                accountOperationRepository.save(accountOperation);
            });

            // --- Affichage ---

            System.out.println("--- List of customers ---");
            customerRepository.findAll().forEach(customer ->{
                System.out.println("id => "+customer.getId()+"\nname => "+customer.getName()+"\nemail => "+customer.getEmail()+"\n");
            });

            System.out.println("--- List of bank accounts ---");
            bankAccountRepository.findAll().forEach(bankAccount -> {
                System.out.println("id => " + bankAccount.getId() + "\ncreationDate => " + bankAccount.getCreationDate() + "\nbalance => " + bankAccount.getBalance());
                System.out.println("costumer name => " + bankAccount.getCustomer().getName());
            });

            System.out.println("--- List of operations ---");
            accountOperationRepository.findAll().forEach(operation->{
                System.out.println("id => "+operation.getId()+"\ndate => "+operation.getDate()+"\namount => "+operation.getAmount());
            });
        };
    }
}
