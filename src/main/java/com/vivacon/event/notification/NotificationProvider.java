package com.vivacon.event.notification;

import com.vivacon.entity.Notification;

public interface NotificationProvider {

    void sendNotification(Notification notification);
}
