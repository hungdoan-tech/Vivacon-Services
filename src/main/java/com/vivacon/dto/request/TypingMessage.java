package com.vivacon.dto.request;

import com.vivacon.dto.request.MessageRequest;

public class TypingMessage extends MessageRequest {

    private String username;

    private boolean isTyping;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isTyping() {
        return isTyping;
    }

    public void setTyping(boolean typing) {
        isTyping = typing;
    }
}
