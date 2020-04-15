package com.bymbank.emailingsystem.services;

import com.bymbank.emailingsystem.models.Account;
import com.bymbank.emailingsystem.models.User;

import javax.mail.MessagingException;

public interface AccountService {
    void notifyUserAccountAction(Long accountId, Long transactionId);
    void notifyTellerAccountAction(Long accountId, Long transactionId, Long tellerId);
}
