package com.youssef.backend.repositories;

import com.youssef.backend.entities.BankAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, String> {
}
