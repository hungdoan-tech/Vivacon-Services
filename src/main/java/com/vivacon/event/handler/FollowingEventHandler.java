package com.vivacon.event.handler;

import com.vivacon.entity.Account;
import com.vivacon.entity.Notification;
import com.vivacon.event.FollowingEvent;
import com.vivacon.event.notification_provider.NotificationProvider;
import com.vivacon.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static com.vivacon.entity.enum_type.NotificationType.FOLLOWING_ON_ME;

@Component
public class FollowingEventHandler {

    @Qualifier("emailSender")
    private NotificationProvider emailSender;

    @Qualifier("emailSender")
    private NotificationProvider websocketSender;

    private NotificationRepository notificationRepository;

    public FollowingEventHandler(NotificationProvider emailSender,
                                 NotificationProvider websocketSender,
                                 NotificationRepository notificationRepository) {
        this.emailSender = emailSender;
        this.websocketSender = websocketSender;
        this.notificationRepository = notificationRepository;
    }

    @Async
    @EventListener
    public void onApplicationEvent(FollowingEvent followingEvent) {
        Account formAccount = followingEvent.getFollowing().getFromAccount();
        Account toAccount = followingEvent.getFollowing().getToAccount();
        Notification notification = createFollowingNotification(formAccount, toAccount);
        Notification savedNotification = notificationRepository.saveAndFlush(notification);
        websocketSender.sendNotification(savedNotification);
    }

    private Notification createFollowingNotification(Account fromAccount, Account toAccount) {
        String content = fromAccount.getFullName() + " start following you";
        String title = "Someone follows you";
        return new Notification(FOLLOWING_ON_ME, fromAccount, toAccount, fromAccount.getId(), title, content);
    }
}
