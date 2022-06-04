package com.vivacon.event.handler;

import com.vivacon.entity.Account;
import com.vivacon.entity.Notification;
import com.vivacon.entity.Post;
import com.vivacon.entity.enum_type.MessageStatus;
import com.vivacon.event.LikeCreatingEvent;
import com.vivacon.event.notification_provider.NotificationProvider;
import com.vivacon.repository.LikeRepository;
import com.vivacon.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.vivacon.entity.enum_type.NotificationType.LIKE_ON_POST;

@Component
public class LikeCreatingEventHandler {

    @Qualifier("emailSender")
    private NotificationProvider emailSender;

    @Qualifier("emailSender")
    private NotificationProvider websocketSender;

    private NotificationRepository notificationRepository;

    private LikeRepository likeRepository;

    public LikeCreatingEventHandler(NotificationProvider emailSender,
                                    NotificationProvider websocketSender,
                                    NotificationRepository notificationRepository,
                                    LikeRepository likeRepository) {
        this.emailSender = emailSender;
        this.websocketSender = websocketSender;
        this.notificationRepository = notificationRepository;
        this.likeRepository = likeRepository;
    }

    @Async
    @EventListener
    public void onApplicationEvent(LikeCreatingEvent likeCreatingEvent) {
        Account likeAuthor = likeCreatingEvent.getLike().getAccount();
        Post post = likeCreatingEvent.getLike().getPost();

        if (likeAuthor.getId() != post.getCreatedBy().getId()) {
            Optional<Notification> existingNotification = notificationRepository.findByTypeAndDomainId(LIKE_ON_POST, post.getId());

            if (!existingNotification.isPresent()) {
                Notification notification = createCommentEvent(likeAuthor, post);
                Notification savedNotification = notificationRepository.saveAndFlush(notification);
                websocketSender.sendNotification(savedNotification);
            } else {
                Notification updatedNotification = updateTheContent(existingNotification.get(), likeAuthor, post);
                Notification savedNotification = notificationRepository.saveAndFlush(updatedNotification);
                websocketSender.sendNotification(savedNotification);
            }
        }
    }

    private Notification updateTheContent(Notification notification, Account likeAuthor, Post post) {

        Long likeCount = likeRepository.getCountingLike(post.getId()) - 1;
        String displayOtherLikeCount = (likeCount > 0) ? " and " + likeCount + " others " : "";
        String content = likeAuthor.getFullName() + displayOtherLikeCount + " like your post";
        notification.setContent(content);
        notification.setTimestamp(LocalDateTime.now());
        notification.setStatus(MessageStatus.SENT);
        return notification;
    }

    private Notification createCommentEvent(Account likeAuthor, Post post) {

        Long likeCount = likeRepository.getCountingLike(post.getId()) - 1;
        String displayOtherLikeCount = (likeCount > 0) ? " and " + likeCount + " others " : "";
        String content = likeAuthor.getFullName() + displayOtherLikeCount + " like your post";
        return new Notification(LIKE_ON_POST, likeAuthor, post.getCreatedBy(), post.getId(),
                "New like on your post", content);
    }
}
