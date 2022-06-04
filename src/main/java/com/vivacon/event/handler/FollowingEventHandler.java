package com.vivacon.event.handler;

import com.vivacon.entity.Account;
import com.vivacon.entity.Attachment;
import com.vivacon.entity.Notification;
import com.vivacon.event.FollowingEvent;
import com.vivacon.event.notification.NotificationProvider;
import com.vivacon.repository.AttachmentRepository;
import com.vivacon.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.vivacon.common.constant.Constants.BLANK_AVATAR_URL;
import static com.vivacon.entity.enum_type.NotificationType.FOLLOWING_ON_ME;

@Component
public class FollowingEventHandler implements ApplicationListener<FollowingEvent> {

    @Qualifier("emailSender")
    private NotificationProvider emailSender;

    @Qualifier("emailSender")
    private NotificationProvider websocketSender;

    private NotificationRepository notificationRepository;

    private AttachmentRepository attachmentRepository;

    public FollowingEventHandler(NotificationProvider emailSender,
                                 NotificationProvider websocketSender,
                                 AttachmentRepository attachmentRepository,
                                 NotificationRepository notificationRepository) {
        this.emailSender = emailSender;
        this.websocketSender = websocketSender;
        this.attachmentRepository = attachmentRepository;
        this.notificationRepository = notificationRepository;
    }

    @Async
    @Override
    public void onApplicationEvent(FollowingEvent followingEvent) {
        Account formAccount = followingEvent.getFollowing().getFromAccount();
        Account toAccount = followingEvent.getFollowing().getToAccount();
        Notification notification = createFollowingNotification(formAccount, toAccount);
        Notification savedNotification = notificationRepository.saveAndFlush(notification);
        websocketSender.sendNotification(savedNotification);
    }

    private Notification createFollowingNotification(Account fromAccount, Account toAccount) {
        Optional<Attachment> fromAccountAvatar = attachmentRepository.findFirstByProfileIdOrderByTimestampDesc(fromAccount.getId());
        String fromAccountAvatarUrl = fromAccountAvatar.isPresent() ? fromAccountAvatar.get().getUrl() : BLANK_AVATAR_URL;

        String content = fromAccount.getFullName() + " start following you";
        String title = "Someone loves and follows you";

        return new Notification(FOLLOWING_ON_ME, fromAccount.getId(), toAccount, title
                , content, fromAccountAvatarUrl, LocalDateTime.now());
    }
}
