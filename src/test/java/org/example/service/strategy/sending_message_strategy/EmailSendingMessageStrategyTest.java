package org.example.service.strategy.sending_message_strategy;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.example.config.strategy.sending_message_strategy.EmailSendingMessageStrategyConfigProperties;
import org.example.entity.Account;
import org.instancio.junit.InstancioExtension;
import org.instancio.junit.InstancioSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, InstancioExtension.class})
class EmailSendingMessageStrategyTest {

    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private EmailSendingMessageStrategyConfigProperties configProperties;
    @Mock
    private MimeMessage mimeMessage;
    private EmailSendingMessageStrategy emailSendingMessageStrategy;

    @BeforeEach
    void setUp() {
        emailSendingMessageStrategy = new EmailSendingMessageStrategy(javaMailSender, configProperties);
    }

    @ParameterizedTest
    @InstancioSource
    void shouldSendEmailMessageToAccount(Account account, String emailFrom, String message) throws MessagingException {
        when(configProperties.getUsername())
                .thenReturn(emailFrom);
        when(configProperties.getMessage())
                .thenReturn(message);
        when(javaMailSender.createMimeMessage())
                .thenReturn(mimeMessage);

        emailSendingMessageStrategy.sendMessageToAccount(account);

        verify(mimeMessage).setFrom(new InternetAddress(emailFrom));
        verify(mimeMessage).setRecipient(any(), eq(new InternetAddress(account.getEmail())));
        verify(mimeMessage).setText(contains(message));
    }
}