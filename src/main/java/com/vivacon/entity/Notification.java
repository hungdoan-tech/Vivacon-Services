package com.vivacon.entity;

import com.vivacon.entity.enum_type.MessageStatus;
import com.vivacon.entity.enum_type.NotificationType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_id_generator")
    @SequenceGenerator(name = "notification_id_generator", sequenceName = "notification_id_seq", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private NotificationType type;

    @Column(name = "domain_Id")
    private Long domainId;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "action_author_id")
    private Account actionAuthor;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "receiver_id")
    private Account receiver;

    private String title;

    private String content;

    private MessageStatus status;

    private LocalDateTime timestamp;

    public Notification() {
    }

    public Notification(String subject, String content, Account account) {
    }

    public Notification(NotificationType type, Account actionAuthor, Account receiver, Long domainId, String title, String content) {
        this.type = type;
        this.domainId = domainId;
        this.receiver = receiver;
        this.title = title;
        this.content = content;
        this.actionAuthor = actionAuthor;
        this.timestamp = LocalDateTime.now();
        this.status = MessageStatus.SENT;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Account getActionAuthor() {
        return actionAuthor;
    }

    public void setActionAuthor(Account actionAuthor) {
        this.actionAuthor = actionAuthor;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }
}
