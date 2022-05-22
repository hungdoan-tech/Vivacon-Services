package com.vivacon.dto.request;

import com.vivacon.dto.request.MessageRequest;

public class TypingMessage extends MessageRequest {

    private boolean isTyping;

    public boolean getIsTyping() {
        return isTyping;
    }

    public void setIsTyping(boolean typing) {
        isTyping = typing;
    }
}
