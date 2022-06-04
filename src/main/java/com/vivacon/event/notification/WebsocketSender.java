package com.vivacon.event.notification;

import com.vivacon.dto.response.NotificationResponse;
import com.vivacon.entity.Notification;
import com.vivacon.mapper.NotificationMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import static com.vivacon.common.constant.Constants.NOTIFICATION_QUEUE_DESTINATION;
import static com.vivacon.common.constant.Constants.USERNAME_PLACEHOLDER;

@Component
public class WebsocketSender implements NotificationProvider {
    private SimpMessagingTemplate messagingTemplate;

    private NotificationMapper notificationMapper;

    public WebsocketSender(SimpMessagingTemplate messagingTemplate,
                           NotificationMapper notificationMapper) {
        this.messagingTemplate = messagingTemplate;
        this.notificationMapper = notificationMapper;
    }

    @Override
    public void sendNotification(Notification notification) {
        String path = NOTIFICATION_QUEUE_DESTINATION.replace(USERNAME_PLACEHOLDER, notification.getReceiver().getUsername());
        NotificationResponse notificationResponse = notificationMapper.toResponse(notification);
        this.messagingTemplate.convertAndSend(path, notificationResponse);
    }
}
