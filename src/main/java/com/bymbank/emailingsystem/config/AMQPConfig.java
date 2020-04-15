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
    public MessageConverter messageConverter()
    {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        return new Jackson2JsonMessageConverter(mapper);
    }

}
