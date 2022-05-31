package com.vivacon.event.handler;

import com.vivacon.entity.Comment;
import com.vivacon.entity.Notification;
import com.vivacon.entity.NotificationType;
import com.vivacon.event.CommentCreatingEvent;
import com.vivacon.event.notification.NotificationProvider;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.mapper.NotificationMapper;
import com.vivacon.repository.AttachmentRepository;
import com.vivacon.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.vivacon.common.constant.Constants.CONNECTED_NAME_TOKEN;
import static com.vivacon.entity.NotificationType.COMMENT_ON_POST;
import static com.vivacon.entity.NotificationType.REPLY_ON_COMMENT;

@Component
public class NotificationEventHandler {

    @Qualifier("emailSender")
    private NotificationProvider emailSender;

    @Qualifier("emailSender")
    private NotificationProvider websocketSender;

    private NotificationRepository notificationRepository;

    private AttachmentRepository attachmentRepository;

    private NotificationMapper notificationMapper;

    public NotificationEventHandler(NotificationProvider emailSender,
                                    NotificationProvider websocketSender,
                                    NotificationMapper notificationMapper,
                                    AttachmentRepository attachmentRepository,
                                    NotificationRepository notificationRepository) {
        this.notificationMapper = notificationMapper;
        this.emailSender = emailSender;
        this.websocketSender = websocketSender;
        this.attachmentRepository = attachmentRepository;
        this.notificationRepository = notificationRepository;
    }

    @Async
    @EventListener
    public void handleCommentCreatingEvent(CommentCreatingEvent commentCreatingEvent) {
        Comment comment = commentCreatingEvent.getComment();
        if (comment.getPost().getCreatedBy().getId() != comment.getCreatedBy().getId()) {

            Notification notification = null;
            NotificationType type = (comment.getParentComment() == null) ? COMMENT_ON_POST : REPLY_ON_COMMENT;
            Optional<Notification> existingNotification = notificationRepository.findByTypeAndDomainId(type, comment.getId());
            String authors = comment.getCreatedBy().getFullName();

            if (existingNotification.isPresent()) {
                notification = updateExistingCommentNotification(existingNotification, authors);
            } else {
                notification = createNewCommentNotification(comment, type, authors);
            }
            Notification savedNotification = notificationRepository.save(notification);
            websocketSender.sendNotification(savedNotification);
        }
    }


    private Notification updateExistingCommentNotification(Optional<Notification> existingNotification, String authors) {
        String existingAuthor = existingNotification.get().getContent();
        if (existingAuthor.contains(authors) == false) {
            authors = existingAuthor + CONNECTED_NAME_TOKEN + authors;
        }
        existingNotification.get().setContent(authors);
        existingNotification.get().setTimestamp(LocalDateTime.now());
        return existingNotification.get();
    }

    private Notification createNewCommentNotification(Comment comment, NotificationType type, String authors) {
        Notification notification = null;
        String firstImageInPost = attachmentRepository.findFirstByPostIdOrderByTimestampAsc(comment.getPost().getId())
                .orElseThrow(RecordNotFoundException::new).getUrl();
        switch (type) {
            case COMMENT_ON_POST: {
                notification = new Notification(type, comment.getId(),
                        comment.getCreatedBy(), "New comments on your post", authors, firstImageInPost, LocalDateTime.now());
                break;
            }
            case REPLY_ON_COMMENT: {
                notification = new Notification(type, comment.getId(),
                        comment.getCreatedBy(), "New reply on your comment", authors, firstImageInPost, LocalDateTime.now());
                break;
            }
            default: {
                return null;
            }
        }
        return notification;
    }
}
