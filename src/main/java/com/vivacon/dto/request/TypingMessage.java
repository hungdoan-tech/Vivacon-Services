package com.vivacon.dto.request;

import com.vivacon.dto.request.MessageRequest;

public class TypingMessage extends MessageRequest {

    private boolean isTyping;

    public boolean isTyping() {
        return isTyping;
    }

    public void setTyping(boolean typing) {
        isTyping = typing;
    }
}
