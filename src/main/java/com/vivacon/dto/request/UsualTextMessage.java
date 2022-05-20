package com.vivacon.dto.request;

import com.vivacon.dto.request.MessageRequest;
import com.vivacon.entity.MessageType;

public class UsualTextMessage extends MessageRequest {

    private String content;

    public UsualTextMessage(){
    }

    public UsualTextMessage(Long conversationId, String content) {
        super(conversationId);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
