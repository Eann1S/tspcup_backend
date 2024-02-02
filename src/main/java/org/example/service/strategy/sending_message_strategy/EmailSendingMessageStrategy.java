package org.example.service.strategy.sending_message_strategy;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.config.strategy.sending_message_strategy.EmailSendingMessageStrategyConfigProperties;
import org.example.entity.Account;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import static org.example.service.strategy.sending_message_strategy.EmailSendingMessageStrategy.EmailMessagePart.SUBJECT;
import static org.example.service.strategy.sending_message_strategy.EmailSendingMessageStrategy.EmailMessagePart.TEXT;


@Component
@Qualifier("email")
@RequiredArgsConstructor
@Slf4j
public class EmailSendingMessageStrategy implements SendingMessageStrategy {

    private final JavaMailSender mailSender;
    private final EmailSendingMessageStrategyConfigProperties configProperties;

    @Override
    @SneakyThrows
    public void sendMessageToAccount(Account account) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

        messageHelper.setSubject(SUBJECT.messagePart);
        messageHelper.setFrom(configProperties.getUsername());
        messageHelper.setTo(account.getEmail());
        messageHelper.setText(TEXT.formatWith(configProperties.getMessage()));

        mailSender.send(mimeMessage);
        log.info("message was sent to {}", account.getEmail());
    }

    enum EmailMessagePart {
        SUBJECT("Следующий шаг."),
        TEXT("Перейдите в данный телеграм канал для последующих инструкций: %s");

        final String messagePart;

        EmailMessagePart(String messagePart) {
            this.messagePart = messagePart;
        }

        String formatWith(Object... params) {
            return messagePart.formatted(params);
        }
    }
}
