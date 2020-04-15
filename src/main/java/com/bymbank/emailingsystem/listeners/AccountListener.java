package com.bymbank.emailingsystem.listeners;

import com.bymbank.emailingsystem.services.AccountService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AccountListener {
    private AccountService accountService;

    public AccountListener(AccountService accountService) {
        this.accountService = accountService;
    }

    @RabbitListener(queues = {"accountActions.user"})
    public void accountActions(Map<String, String> payload) {
        this.accountService.notifyUserAccountAction(
                Long.valueOf(payload.get("accountId")),
                Long.valueOf(payload.get("transactionId"))
        );
    }

    @RabbitListener(queues = {"accountActions.teller"})
    public void tellerAccountActions(Map<String, String> payload) {
        this.accountService.notifyTellerAccountAction(
                Long.valueOf(payload.get("accountId")),
                Long.valueOf(payload.get("transactionId")),
                Long.valueOf(payload.get("tellerId"))
        );
    }
}
