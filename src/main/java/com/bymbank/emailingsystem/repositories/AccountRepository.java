package com.bymbank.emailingsystem.repositories;

import com.bymbank.emailingsystem.models.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends ReadOnlyRepository<Account, Long> {
}
