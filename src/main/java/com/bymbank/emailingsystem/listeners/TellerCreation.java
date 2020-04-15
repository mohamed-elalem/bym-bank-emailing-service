package com.bymbank.emailingsystem.listeners;

import com.bymbank.emailingsystem.services.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TellerCreation {
    private UserService userService;

    public TellerCreation(UserService userService) {
        this.userService = userService;
    }

    @RabbitListener(queues = {"tellerCreation.teller"})
    public void emailTellerForCreation(Map<String, String> payload) {
        this.userService.notifyTellerWithCreation(
                Long.valueOf(payload.get("createdUserId")),
                payload.get("password"));
    }

    @RabbitListener(queues = {"tellerCreation.admin"})
    public void emailAdminForCreation(Map<String, String> payload) {
        this.userService.notifyAdminWithTellerCreation(
                Long.valueOf(payload.get("creatingUserId")),
                Long.valueOf(payload.get("createdUserId"))
        );
    }
}
