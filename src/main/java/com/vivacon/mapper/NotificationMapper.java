package com.vivacon.mapper;

import com.vivacon.dto.response.NotificationResponse;
import com.vivacon.entity.Notification;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    private ModelMapper mapper;

    public NotificationResponse toResponse(Notification notification) {
        NotificationResponse notificationResponse = mapper.map(notification, NotificationResponse.class);
        return notificationResponse;
    }
}
