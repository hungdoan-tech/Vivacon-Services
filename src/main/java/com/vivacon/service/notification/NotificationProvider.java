package com.vivacon.service.notification;

import com.vivacon.entity.Account;
import com.vivacon.entity.Notification;

public interface NotificationProvider {

    void sendNotification(Notification notification);
}
