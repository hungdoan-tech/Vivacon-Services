package com.vivacon.service.notification;

import com.vivacon.entity.Account;

public interface NotificationProvider {

    void sendVerificationCode(Account account, String code);
}
