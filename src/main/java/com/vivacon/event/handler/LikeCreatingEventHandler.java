package com.vivacon.event.handler;

import com.vivacon.entity.Account;
import com.vivacon.entity.Notification;
import com.vivacon.entity.Post;
import com.vivacon.event.LikeCreatingEvent;
import com.vivacon.event.notification.NotificationProvider;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.repository.AttachmentRepository;
import com.vivacon.repository.LikeRepository;
import com.vivacon.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.vivacon.entity.NotificationType.LIKE_ON_POST;

@Component
public class LikeCreatingEventHandler implements ApplicationListener<LikeCreatingEvent> {

    @Qualifier("emailSender")
    private NotificationProvider emailSender;

    @Qualifier("emailSender")
    private NotificationProvider websocketSender;

    private NotificationRepository notificationRepository;

    private AttachmentRepository attachmentRepository;

    private LikeRepository likeRepository;

    public LikeCreatingEventHandler(NotificationProvider emailSender,
                                    NotificationProvider websocketSender,
                                    AttachmentRepository attachmentRepository,
                                    NotificationRepository notificationRepository,
                                    LikeRepository likeRepository) {
        this.emailSender = emailSender;
        this.websocketSender = websocketSender;
        this.attachmentRepository = attachmentRepository;
        this.notificationRepository = notificationRepository;
        this.likeRepository = likeRepository;
    }

    @Async
    @Override
    public void onApplicationEvent(LikeCreatingEvent likeCreatingEvent) {
        Account likeAuthor = likeCreatingEvent.getLike().getAccount();
        Post post = likeCreatingEvent.getLike().getPost();
        if (likeAuthor.getId() != post.getCreatedBy().getId()) {
            Notification notification = createCommentEvent(likeAuthor, post);
            Notification savedNotification = notificationRepository.saveAndFlush(notification);
            websocketSender.sendNotification(savedNotification);
        }
    }

    private Notification createCommentEvent(Account likeAuthor, Post post) {
        String firstImageInPost = attachmentRepository
                .findFirstByPostIdOrderByTimestampAsc(post.getId())
                .orElseThrow(RecordNotFoundException::new).getUrl();
        Long likeCount = likeRepository.getCountingLike(post.getId()) - 1;
        String displayOtherLikeCount = (likeCount > 0) ? " and " + likeCount.toString() + " others " : "";

        String content = likeAuthor.getFullName() + displayOtherLikeCount + " like your post";
        return new Notification(LIKE_ON_POST, post.getId(), post.getCreatedBy(),
                "New like on your post", content, firstImageInPost, LocalDateTime.now());
    }
}
