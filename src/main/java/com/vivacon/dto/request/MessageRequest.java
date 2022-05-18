package com.vivacon.dto.request;

import com.vivacon.entity.MessageType;

public class MessageRequest {

    private Long conversationId;

    private MessageType messageType;

    public MessageRequest() {
    }

    public MessageRequest(Long conversationId, MessageType messageType) {
        this.conversationId = conversationId;
        this.messageType = messageType;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
