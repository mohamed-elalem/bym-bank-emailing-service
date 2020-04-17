package com.bymbank.emailingsystem.listeners;

import com.bymbank.emailingsystem.services.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserCreation {
    private UserService userService;

    public UserCreation(UserService userService) {
        this.userService = userService;
    }

    @RabbitListener(queues = {"userCreation.user"})
    public void emailUserForCreation(Map<String, String> payload) {
        this.userService.notifyUserWithCreation(Long.valueOf(payload.get("createdUserId")), payload.get("password"));
    }

    @RabbitListener(queues = {"userCreation.teller"})
    public void emailTellerForCreation(Map<String, String> payload) {
        this.userService.notifyTellerWithUserCreation(
                Long.valueOf(payload.get("creatingUserId")),
                Long.valueOf(payload.get("createdUserId"))
        );
    }
}
