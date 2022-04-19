package com.vivacon.service.notification;

import com.vivacon.entity.Account;

public interface NotificationProvider {

    void sendNotification(Account recipient, String title, String content);
}
