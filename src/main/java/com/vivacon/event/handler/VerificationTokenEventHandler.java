package com.vivacon.event.handler;

import com.vivacon.entity.Account;
import com.vivacon.event.GeneratingVerificationTokenEvent;
import com.vivacon.event.RegistrationCompleteEvent;
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
public class VerificationTokenEventHandler {

    private Environment environment;

    @Qualifier("emailSender")
    private NotificationProvider emailSender;

    private AccountRepository accountRepository;

    private Random random = new Random();

    public VerificationTokenEventHandler(NotificationProvider emailSender,
                                         AccountRepository accountRepository,
                                         Environment environment) {
        this.emailSender = emailSender;
        this.accountRepository = accountRepository;
        this.environment = environment;
    }

    @Async
    @EventListener
    public void handleUserRegistration(RegistrationCompleteEvent userRegistrationEvent) {
        Account account = userRegistrationEvent.getAccount();
        String code = generateVerificationCodePerUsername(account);
        Integer expirationInMinutes = Integer.valueOf(environment.getProperty("vivacon.verification_token.expiration")) / 60000;

        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br/>"
                + "Please use the code below to verify your registration:<br/>"
                + "<h3>[[code]]</h3><br/>"
                + "Please notice that your code is unique and only take effect in <strong> [[expirationTime]] minutes</strong><br/>"
                + "Thank you,<br/>"
                + "Vivacon Service.";
        content = content.replace("[[name]]", account.getFullName());
        content = content.replace("[[code]]", code);
        content = content.replace("[[expirationTime]]", String.valueOf(expirationInMinutes));

        emailSender.sendNotification(account, subject, content);
    }

    @Async
    @EventListener
    public void handleGeneratingVerificationToken(GeneratingVerificationTokenEvent generatingVerificationTokenEvent) {
        Account account = generatingVerificationTokenEvent.getAccount();
        String code = generateVerificationCodePerUsername(account);
        Integer expirationInMinutes = Integer.valueOf(environment.getProperty("vivacon.verification_token.expiration")) / 60000;

        String subject = "Renew your verification token";
        String content = "Dear [[name]],<br/>"
                + "Please use the code below to verify your account:<br/>"
                + "<h3>[[code]]</h3><br/>"
                + "Please notice that your code is unique and only take effect in <strong> [[expirationTime]] minutes</strong><br/>"
                + "Thank you,<br/>"
                + "Vivacon Service.";
        content = content.replace("[[name]]", account.getFullName());
        content = content.replace("[[code]]", code);
        content = content.replace("[[expirationTime]]", String.valueOf(expirationInMinutes));

        emailSender.sendNotification(account, subject, content);
    }


    private String generateVerificationCodePerUsername(Account account) {
        int number = random.nextInt(999999);
        String code = String.format("%06d", number);
        Integer expirationInstant = Integer.valueOf(environment.getProperty("vivacon.verification_token.expiration"));

        account.setVerificationToken(code);
        account.setVerificationExpiredDate(Instant.now().plusMillis(expirationInstant));
        accountRepository.saveAndFlush(account);
        return code;
    }
}
