package com.vivacon.event.handler;

import com.vivacon.entity.Account;
import com.vivacon.entity.Comment;
import com.vivacon.entity.Notification;
import com.vivacon.entity.NotificationType;
import com.vivacon.event.CommentCreatingEvent;
import com.vivacon.event.notification.NotificationProvider;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.repository.AccountRepository;
import com.vivacon.repository.AttachmentRepository;
import com.vivacon.repository.CommentRepository;
import com.vivacon.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.vivacon.entity.NotificationType.AWARE_ON_COMMENT;
import static com.vivacon.entity.NotificationType.COMMENT_ON_POST;
import static com.vivacon.entity.NotificationType.REPLY_ON_COMMENT;

@Component
public class NotificationEventHandler implements ApplicationListener<CommentCreatingEvent> {

    @Qualifier("emailSender")
    private NotificationProvider emailSender;

    @Qualifier("emailSender")
    private NotificationProvider websocketSender;

    private NotificationRepository notificationRepository;

    private AttachmentRepository attachmentRepository;

    private CommentRepository commentRepository;

    private AccountRepository accountRepository;

    public NotificationEventHandler(NotificationProvider emailSender,
                                    NotificationProvider websocketSender,
                                    AttachmentRepository attachmentRepository,
                                    NotificationRepository notificationRepository,
                                    CommentRepository commentRepository,
                                    AccountRepository accountRepository) {
        this.emailSender = emailSender;
        this.websocketSender = websocketSender;
        this.attachmentRepository = attachmentRepository;
        this.notificationRepository = notificationRepository;
        this.commentRepository = commentRepository;
        this.accountRepository = accountRepository;
    }

    @Async
    @Override
    public void onApplicationEvent(CommentCreatingEvent commentCreatingEvent) {
        Comment comment = commentCreatingEvent.getComment();
        boolean isAuthorCommentOnHisPost = comment.getPost().getCreatedBy().getId()
                .equals(comment.getCreatedBy().getId());

        if (!isAuthorCommentOnHisPost) {
            sendCommentOnPostNotification(comment);

            if (comment.getParentComment() != null) {
                sendAwareOnCommentNotification(comment);
                sendReplyOnCommentNotification(comment);
            }
        }
    }

    /**
     * Send notification for the account who own the post
     *
     * @param comment
     */
    private void sendCommentOnPostNotification(Comment comment) {
        Notification notification = createCommentNotification(comment, COMMENT_ON_POST, null);
        Notification savedNotification = notificationRepository.save(notification);
        websocketSender.sendNotification(savedNotification);
    }

    /**
     * Send notification for the account who own the parent comment
     *
     * @param comment
     */
    private void sendReplyOnCommentNotification(Comment comment) {
        Notification notification = createCommentNotification(comment, REPLY_ON_COMMENT, null);
        Notification savedNotification = notificationRepository.save(notification);
        websocketSender.sendNotification(savedNotification);
    }

    /**
     * Send notification for all accounts who aware on the parent comment - they already have the same level child comments
     *
     * @param comment
     */
    private void sendAwareOnCommentNotification(Comment comment) {
        String commentAuthorUsername = comment.getCreatedBy().getUsername();

        commentRepository.findAllChildCommentsByParentCommentId(comment.getParentComment().getId())
                .stream().map(childComment -> childComment.getCreatedBy().getUsername())
                .distinct()
                .filter(username -> !username.equals(commentAuthorUsername))
                .map(username -> accountRepository.findByUsernameIgnoreCase(username)
                        .orElseThrow(RecordNotFoundException::new))
                .forEach(awareAccount -> {
                    Notification notification = createCommentNotification(comment, AWARE_ON_COMMENT, awareAccount);
                    Notification awareNotification = notificationRepository.save(notification);
                    websocketSender.sendNotification(awareNotification);
                });
    }

    /**
     * Create comment notification based on the Notification Type
     *
     * @param comment
     * @param type
     * @param receiver
     * @return
     */
    private Notification createCommentNotification(Comment comment, NotificationType type, Account receiver) {

        Notification notification = null;
        String commentAuthorFullName = comment.getCreatedBy().getFullName();
        String firstImageInPost = attachmentRepository.findFirstByPostIdOrderByTimestampAsc(comment.getPost().getId())
                .orElseThrow(RecordNotFoundException::new).getUrl();

        switch (type) {
            case COMMENT_ON_POST: {
                notification = new Notification(type, comment.getId(),
                        comment.getPost().getCreatedBy(), "New comments on your post", commentAuthorFullName, firstImageInPost, LocalDateTime.now());
                break;
            }
            case REPLY_ON_COMMENT: {
                if (comment.getParentComment() != null) {
                    notification = new Notification(type, comment.getId(),
                            comment.getParentComment().getCreatedBy(), "New reply on your comment", commentAuthorFullName, firstImageInPost, LocalDateTime.now());
                }
                break;
            }
            case AWARE_ON_COMMENT: {
                if (receiver != null) {
                    notification = new Notification(type, comment.getId(),
                            receiver, "New comment on your aware comment", commentAuthorFullName, firstImageInPost, LocalDateTime.now());
                }
                break;
            }
            default: {
                return null;
            }
        }
        return notification;
    }
}
