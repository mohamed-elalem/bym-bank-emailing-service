package com.bymbank.emailingsystem.services.impl;

import com.bymbank.emailingsystem.models.Account;
import com.bymbank.emailingsystem.models.Transaction;
import com.bymbank.emailingsystem.models.TransactionType;
import com.bymbank.emailingsystem.models.User;
import com.bymbank.emailingsystem.repositories.AccountRepository;
import com.bymbank.emailingsystem.repositories.TransactionRepository;
import com.bymbank.emailingsystem.repositories.UserRepository;
import com.bymbank.emailingsystem.services.AccountService;
import com.bymbank.emailingsystem.services.EmailService;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final TransactionRepository transationRepository;
    private final TemplateEngine templateEngine;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
                              TransactionRepository transactionRepository,
                              TemplateEngine templateEngine,
                              EmailService emailService,
                              UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.transationRepository = transactionRepository;
        this.templateEngine = templateEngine;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    @Override
    public void notifyUserAccountAction(Long accountId, Long transactionId) {
        Account account = accountRepository.findById(accountId).get();
        User user = account.getUser();
        Transaction transaction = transationRepository.findById(transactionId).get();
        TransactionType transactionType = transaction.getTransactionType();
        Context ctx = new Context();

        ctx.setVariable("user", user);
        ctx.setVariable("account", account);
        ctx.setVariable("transaction", transaction);
        ctx.setVariable("transactionType", transactionType);

        final String htmlContent = this.templateEngine.process("html/emails/accounts/user-actions.html", ctx);

        try {
            this.emailService.sendEmail(user.getEmail(), String.format("Action \"%s\" has been executed", transactionType.getName()), htmlContent, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyTellerAccountAction(Long accountId, Long transactionId, Long tellerId) {
        Account account = accountRepository.findById(accountId).get();
        User user = account.getUser();
        User teller = userRepository.findById(tellerId).get();
        Transaction transaction = transationRepository.findById(transactionId).get();
        TransactionType transactionType = transaction.getTransactionType();
        Context ctx = new Context();

        ctx.setVariable("user", user);
        ctx.setVariable("account", account);
        ctx.setVariable("teller", teller);
        ctx.setVariable("transaction", transaction);
        ctx.setVariable("transactionType", transactionType);

        final String htmlContent = this.templateEngine.process("html/emails/accounts/teller-actions.html", ctx);

        try {
            this.emailService.sendEmail(user.getEmail(), String.format("Action \"%s\" has been executed", transactionType.getName()), htmlContent, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
