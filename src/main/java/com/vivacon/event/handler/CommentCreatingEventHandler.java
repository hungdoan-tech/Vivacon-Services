package com.vivacon.event.handler;

import com.vivacon.entity.Account;
import com.vivacon.entity.Comment;
import com.vivacon.entity.Notification;
import com.vivacon.entity.NotificationType;
import com.vivacon.entity.Post;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vivacon.entity.NotificationType.AWARE_ON_COMMENT;
import static com.vivacon.entity.NotificationType.COMMENT_ON_POST;
import static com.vivacon.entity.NotificationType.REPLY_ON_COMMENT;

@Component
public class CommentCreatingEventHandler implements ApplicationListener<CommentCreatingEvent> {

    @Qualifier("emailSender")
    private NotificationProvider emailSender;

    @Qualifier("emailSender")
    private NotificationProvider websocketSender;

    private NotificationRepository notificationRepository;

    private AttachmentRepository attachmentRepository;

    private CommentRepository commentRepository;

    private AccountRepository accountRepository;

    public CommentCreatingEventHandler(NotificationProvider emailSender,
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

        List<Notification> notifications = new ArrayList<>();

        if (!isAuthorCommentOnHisPost) {
            notifications.add(createCommentNotification(comment, COMMENT_ON_POST, comment.getPost().getCreatedBy()));

            if (comment.getParentComment() != null) {
                notifications.add(createCommentNotification(comment, REPLY_ON_COMMENT, comment.getParentComment().getCreatedBy()));
                notifications.addAll(createAwareOnCommentNotifications(comment));
            }
        }
        sendCommentNotificationsBasedOnPriority(notifications);
    }

    /**
     * <h1>
     * Send the comment notifications based on the priority which can determine by Notification Type ordinal.
     * </h1>
     *
     * <p>
     * The expected case is when one comment creating event causes one receiver to have two or three comment notifications
     * which has been created based on the context of the current post, current first level comment, current child comment.
     * We need to find out which is the most suitable, unique and which must having the highest priority notification to
     * save to database and send via Notification Providers.
     * </p>
     *
     * @param notifications
     */
    private void sendCommentNotificationsBasedOnPriority(List<Notification> notifications) {

        Map<String, List<Notification>> collect = notifications.stream()
                .collect(Collectors.groupingBy(notification -> notification.getReceiver().getUsername()));

        for (String username : collect.keySet()) {
            List<Notification> notificationsByUsername = collect.get(username);
            if (notificationsByUsername.size() > 1) {

                Comparator<Notification> reverseComparator = (t1, t2) -> {
                    int firstItemPriority = t1.getType().ordinal();
                    int secondItemPriority = t2.getType().ordinal();
                    if (firstItemPriority == secondItemPriority) {
                        return 0;
                    } else {
                        return firstItemPriority < secondItemPriority ? -1 : 1;
                    }
                };
                Collections.sort(notificationsByUsername, reverseComparator);

            }
            Notification highPriorityNotification = notificationsByUsername.get(0);
            Notification savedNotification = notificationRepository.saveAndFlush(highPriorityNotification);
            websocketSender.sendNotification(savedNotification);
        }
    }

    /**
     * Send notification for all accounts who aware on the parent comment - they already have the same level child comments
     *
     * @param comment
     */
    private List<Notification> createAwareOnCommentNotifications(Comment comment) {
        String commentAuthorUsername = comment.getCreatedBy().getUsername();

        return commentRepository.findAllChildCommentsByParentCommentId(comment.getParentComment().getId())
                .stream().map(childComment -> childComment.getCreatedBy().getUsername())
                .distinct()
                .filter(username -> !username.equals(commentAuthorUsername))
                .map(username -> accountRepository.findByUsernameIgnoreCase(username)
                        .orElseThrow(RecordNotFoundException::new))
                .map(awareAccount -> createCommentNotification(comment, AWARE_ON_COMMENT, awareAccount))
                .collect(Collectors.toList());
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
        Post post = comment.getPost();
        String firstImageInPost = attachmentRepository.findFirstByPostIdOrderByTimestampAsc(comment.getPost().getId())
                .orElseThrow(RecordNotFoundException::new).getUrl();

        switch (type) {
            case COMMENT_ON_POST: {
                long countingTotalComments = commentRepository.getCountingCommentsByPost(post.getId()) - 1;

                notification = new Notification(type, comment.getId(),
                        comment.getPost().getCreatedBy(), "New comments on your post", commentAuthorFullName + "has comment on your post",
                        firstImageInPost, LocalDateTime.now());
                break;
            }
            case REPLY_ON_COMMENT: {
                if (comment.getParentComment() != null) {
                    notification = new Notification(type, comment.getId(), comment.getParentComment().getCreatedBy(),
                            "New reply on your comment", commentAuthorFullName, firstImageInPost, LocalDateTime.now());
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
                throw new RuntimeException("Not suitable type for creating comment notification");
            }
        }
        return notification;
    }
}
