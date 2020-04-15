package com.bymbank.emailingsystem.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfig {

    @Bean
    public Declarables tellerCreationBinding() {
        Queue tellerCreationQueue = new Queue("tellerCreation.teller", true);
        Queue adminTellerCreationQueue = new Queue("tellerCreation.admin", true);
        FanoutExchange tellerCreationExchange = new FanoutExchange("tellerCreation");
        return new Declarables(
                tellerCreationQueue,
                adminTellerCreationQueue,
                tellerCreationExchange,
                BindingBuilder.bind(tellerCreationQueue).to(tellerCreationExchange),
                BindingBuilder.bind(adminTellerCreationQueue).to(tellerCreationExchange)
        );
    }

    @Bean
    public Declarables userCreationBinding() {
        Queue userCreationQueue = new Queue("userCreation.user", true);
        Queue tellerUserCreationQueue = new Queue("userCreation.teller", true);

        FanoutExchange userCreationExchange = new FanoutExchange("userCreation");

        return new Declarables(
                userCreationQueue,
                tellerUserCreationQueue,
                userCreationExchange,
                BindingBuilder.bind(userCreationQueue).to(userCreationExchange),
                BindingBuilder.bind(tellerUserCreationQueue).to(userCreationExchange)
        );
    }

    @Bean
    public Declarables accountExchangesBinding() {
        Queue accountUserActionsQueue = new Queue("accountActions.user", true);
        Queue accountTellerActionsQueue = new Queue("accountActions.teller", true);
        FanoutExchange accountActionsExchange = new FanoutExchange("accountActions");

        return new Declarables(
                accountUserActionsQueue,
                accountTellerActionsQueue,
                accountActionsExchange,
                BindingBuilder.bind(accountUserActionsQueue).to(accountActionsExchange),
                BindingBuilder.bind(accountTellerActionsQueue).to(accountActionsExchange));
    }

    @Bean
    public Declarables accountLoanBinding() {
        Queue userLoanQueue = new Queue("loan.user", true);
        Queue tellerLoanQueue = new Queue("loan.teller", true);
        Queue successfullMontlyLoan = new Queue("loan.monthly.success", true);
        Queue completedMontlyUserLoan = new Queue("loan.montly.completed.user", true);
        Queue completedMontlyTellerLoan = new Queue("loan.montly.completed.teller", true);
        Queue failedMontlyUserLoan = new Queue("loan.montly.failed.user", true);
        Queue failedMontlyTellerLoan = new Queue("loan.montly.failed.teller", true);
        FanoutExchange loanCreationExchange = new FanoutExchange("loan.creation");
        TopicExchange loanEventsExchange = new TopicExchange("loan.monthly");

        return new Declarables(
                userLoanQueue,
                tellerLoanQueue,
                successfullMontlyLoan,
                completedMontlyUserLoan,
                completedMontlyTellerLoan,
                failedMontlyUserLoan,
                failedMontlyTellerLoan,
                loanCreationExchange,
                loanEventsExchange,
                BindingBuilder.bind(userLoanQueue).to(loanCreationExchange),
                BindingBuilder.bind(tellerLoanQueue).to(loanCreationExchange),
                BindingBuilder.bind(successfullMontlyLoan).to(loanEventsExchange).with("success"),
                BindingBuilder.bind(completedMontlyUserLoan).to(loanEventsExchange).with("complete"),
                BindingBuilder.bind(completedMontlyTellerLoan).to(loanEventsExchange).with("complete"),
                BindingBuilder.bind(failedMontlyTellerLoan).to(loanEventsExchange).with("failure"),
                BindingBuilder.bind(failedMontlyUserLoan).to(loanEventsExchange).with("failure")
        );
    }

    @Bean
    public MessageConverter messageConverter()
    {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        return new Jackson2JsonMessageConverter(mapper);
    }

}
