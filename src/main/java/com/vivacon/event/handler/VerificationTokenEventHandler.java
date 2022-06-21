package com.vivacon.event.handler;

import com.vivacon.entity.Account;
import com.vivacon.entity.Notification;
import com.vivacon.entity.Setting;
import com.vivacon.entity.enum_type.SettingType;
import com.vivacon.event.GeneratingVerificationTokenEvent;
import com.vivacon.event.RegistrationCompleteEvent;
import com.vivacon.event.notification_provider.NotificationProvider;
import com.vivacon.repository.AccountRepository;
import com.vivacon.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class VerificationTokenEventHandler {

    private Environment environment;

    @Qualifier("emailSender")
    private NotificationProvider emailSender;

    private AccountRepository accountRepository;

    private SettingRepository settingRepository;

    private int verifiedTokenExpirationInMiliseconds;

    private Random random = new Random();

    public VerificationTokenEventHandler(NotificationProvider emailSender,
                                         AccountRepository accountRepository,
                                         SettingRepository settingRepository,
                                         Environment environment) {
        this.emailSender = emailSender;
        this.accountRepository = accountRepository;
        this.settingRepository = settingRepository;
        this.environment = environment;
    }

    @PostConstruct
    private void operatePostConstruction() {
        this.verifiedTokenExpirationInMiliseconds = Integer.valueOf(environment.getProperty("vivacon.verification_token.expiration"));
    }

    @Async
    @EventListener
    public void handleUserRegistration(RegistrationCompleteEvent userRegistrationEvent) {
        Account account = userRegistrationEvent.getAccount();
        saveDefaultSettingsForNewAccount(account);
        sendEmailOnUserRegistrationComplete(account);
    }

    private List<Setting> saveDefaultSettingsForNewAccount(Account account) {
        List<Setting> settings = new ArrayList<>();
        SettingType[] settingTypes = SettingType.class.getEnumConstants();
        for (SettingType type : settingTypes) {
            settings.add(new Setting(account, type, type.getDefaultValue()));
        }
        return settingRepository.saveAllAndFlush(settings);
    }

    private void sendEmailOnUserRegistrationComplete(Account account) {
        String code = generateVerificationCodePerUsername(account);
        Integer expirationInMinutes = verifiedTokenExpirationInMiliseconds / 60000;

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

        Notification notification = new Notification(subject, content, account);
        emailSender.sendNotification(notification);
    }

    @Async
    @EventListener
    public void handleGeneratingVerificationToken(GeneratingVerificationTokenEvent generatingVerificationTokenEvent) {
        Account account = generatingVerificationTokenEvent.getAccount();
        String code = generateVerificationCodePerUsername(account);
        Integer expirationInMinutes = verifiedTokenExpirationInMiliseconds / 60000;

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

        Notification notification = new Notification(subject, content, account);
        emailSender.sendNotification(notification);
    }


    private String generateVerificationCodePerUsername(Account account) {
        int number = random.nextInt(999999);
        String code = String.format("%06d", number);

        account.setVerificationToken(code);
        account.setVerificationExpiredDate(Instant.now().plusMillis(verifiedTokenExpirationInMiliseconds));
        accountRepository.saveAndFlush(account);
        return code;
    }
}
