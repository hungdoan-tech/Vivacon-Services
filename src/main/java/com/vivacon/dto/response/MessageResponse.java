package com.vivacon.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vivacon.common.enum_type.MessageStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MessageResponse {

    private Long id;

    private AccountResponse sender;

    private Long conversationId;

    private String content;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private MessageStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountResponse getSender() {
        return sender;
    }

    public void setSender(AccountResponse sender) {
        this.sender = sender;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
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

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }
}
