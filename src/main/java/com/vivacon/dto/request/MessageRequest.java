package com.vivacon.dto.request;

import com.vivacon.entity.MessageType;

public class MessageRequest {

    private Long conversationId;

    public MessageRequest() {
    }

    public MessageRequest(Long conversationId) {
        this.conversationId = conversationId;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }
}
