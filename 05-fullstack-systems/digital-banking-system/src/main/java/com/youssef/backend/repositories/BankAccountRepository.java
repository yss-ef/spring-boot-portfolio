package com.youssef.backend.repositories;

import com.youssef.backend.dtos.BankAccountDTO;
import com.youssef.backend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    public List<BankAccount> findBankAccountsByCustomerId(Long customerId);
}
