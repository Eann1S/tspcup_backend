package org.example.config.service;

import lombok.RequiredArgsConstructor;
import org.example.service.AccountService;
import org.example.service.RegisterService;
import org.example.service.impl.RegisterServiceImpl;
import org.example.service.strategy.sending_message_strategy.SendingMessageStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RegisterServiceConfiguration {

    private final AccountService accountService;

    @Bean
    @Qualifier("email")
    public RegisterService registerService(
            @Qualifier("email") SendingMessageStrategy sendingMessageStrategy
    ) {
        return new RegisterServiceImpl(accountService, sendingMessageStrategy);
    }
}
