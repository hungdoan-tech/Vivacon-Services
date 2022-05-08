package com.vivacon.dto.response;

public class OutlineConversation {

    private Long id;

    private String name;

    private MessageResponse latestMessage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MessageResponse getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(MessageResponse latestMessage) {
        this.latestMessage = latestMessage;
    }
}
