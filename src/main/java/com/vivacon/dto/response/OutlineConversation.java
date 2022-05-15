package com.vivacon.dto.response;

import java.util.List;

public class OutlineConversation {

    private Long id;

    private String name;

    private MessageResponse latestMessage;
    private List<EssentialAccount> participants;

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

    public List<EssentialAccount> getParticipants() {
        return participants;
    }

    public void setParticipants(List<EssentialAccount> participants) {
        this.participants = participants;
    }
}
