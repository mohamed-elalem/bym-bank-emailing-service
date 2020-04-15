package com.bymbank.emailingsystem.repositories;

import com.bymbank.emailingsystem.models.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends ReadOnlyRepository<Transaction, Long> {
}
