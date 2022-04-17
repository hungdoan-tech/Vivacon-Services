package com.vivacon.service.notification;

import com.vivacon.entity.Account;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@Qualifier("emailSender")
public class EmailSender implements NotificationProvider {

    private JavaMailSender mailSender;

    public EmailSender(JavaMailSender javaMailSender) {
        this.mailSender = javaMailSender;
    }

    @Override
    public void sendVerificationCode(Account account, String code) {
        String toAddress = account.getEmail();
        String fromAddress = "vivacon.service@gmail.com";
        String senderName = "Vivacon Social Media Company";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click use the code below to verify your registration:<br>"
                + "<h3>[[code]]</h3>"
                + "Please notice that your code is unique and only take effect in 5 minutes"
                + "Thank you,<br>"
                + "Vivacon Service.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        content = content.replace("[[name]]", account.getFullName());
        content = content.replace("[[code]]", code);
        try {
            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
