package com.vivacon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.stereotype.Indexed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Indexed
@Table(name = "chat_message")
@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Account sender;

    @ManyToOne(targetEntity = Conversation.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private Conversation recipient;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "timestamp", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDate timestamp;

    @Column(nullable = false)
    private Boolean active;

    public ChatMessage() {
    }

    public ChatMessage(Account sender, Conversation recipient, String message, LocalDate timestamp, Boolean active) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = message;
        this.timestamp = timestamp;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Conversation getRecipient() {
        return recipient;
    }

    public void setRecipient(Conversation recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String message) {
        this.content = message;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}