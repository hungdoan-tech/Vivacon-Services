package com.vivacon.mapper;

import com.vivacon.dto.response.OutlineConversation;
import com.vivacon.entity.Conversation;
import com.vivacon.entity.Message;
import com.vivacon.repository.MessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConversationMapper {

    private ModelMapper mapper;

    private MessageRepository messageRepository;

    private MessageMapper messageMapper;

    public ConversationMapper(ModelMapper modelMapper,
                              MessageMapper messageMapper,
                              MessageRepository messageRepository) {
        this.mapper = modelMapper;
        this.messageMapper = messageMapper;
        this.messageRepository = messageRepository;
    }

    public OutlineConversation toOutlineConversation(Conversation conversation) {
        OutlineConversation outlineConversation = mapper.map(conversation, OutlineConversation.class);
        Optional<Message> latestMessage = messageRepository.findFirstByRecipientIdOrderByTimestampDesc(conversation.getId());
        if (latestMessage.isPresent()) {
            outlineConversation.setLatestMessage(messageMapper.toResponse(latestMessage.get()));
        }
        return outlineConversation;
    }
}
