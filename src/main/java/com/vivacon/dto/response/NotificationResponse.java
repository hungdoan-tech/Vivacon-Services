package com.vivacon.dto.response;

import com.vivacon.entity.Account;
import com.vivacon.entity.NotificationType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;

public enum NotificationResponse {

    private Long id;

    private NotificationType type;

    private Long domainId;

    private String title;

    private String content;

    private String image;

    private LocalDateTime timestamp;
}
