package com.vivacon.event.handler;

import com.vivacon.entity.Account;
import com.vivacon.event.RegistrationCompleteEvent;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.repository.AccountRepository;
import com.vivacon.service.notification.NotificationProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Random;

@Component
public class RegistrationEventHandler {

    @Qualifier("emailSender")
    private NotificationProvider emailSender;

    private AccountRepository accountRepository;

    private Environment environment;

    public RegistrationEventHandler(NotificationProvider emailSender,
                                    AccountRepository accountRepository,
                                    Environment environment) {
        this.emailSender = emailSender;
        this.accountRepository = accountRepository;
        this.environment = environment;
    }

    @Async
    @EventListener
    public void handleUserRegistration(RegistrationCompleteEvent userRegistrationEvent) {
        String username = userRegistrationEvent.getUsername();
        Account account = accountRepository.findByUsernameIgnoreCase(username).orElseThrow(RecordNotFoundException::new);
        String code = generateVerificationCodePerUsername(account);
        emailSender.sendVerificationCode(account, code);
    }

    private String generateVerificationCodePerUsername(Account account) {
        Integer expirationInstant = Integer.valueOf(environment.getProperty("vivacon.verification_token.expiration"));
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        String code = String.format("%06d", number);

        account.setVerificationToken(code);
        account.setVerificationExpiredDate(Instant.now().plusMillis(expirationInstant));
        accountRepository.saveAndFlush(account);
        return code;
    }
}
