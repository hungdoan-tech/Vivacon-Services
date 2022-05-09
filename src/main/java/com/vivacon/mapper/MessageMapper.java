package com.vivacon.mapper;

import com.vivacon.common.enum_type.MessageStatus;
import com.vivacon.dto.request.MessageRequest;
import com.vivacon.dto.response.AccountResponse;
import com.vivacon.dto.response.MessageResponse;
import com.vivacon.entity.Account;
import com.vivacon.entity.Conversation;
import com.vivacon.entity.Message;
import com.vivacon.repository.ConversationRepository;
import com.vivacon.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MessageMapper {
    private ModelMapper mapper;

    private AccountMapper accountMapper;

    private AccountService accountService;

    private ConversationRepository conversationRepository;

    public MessageMapper(ModelMapper modelMapper,
                         AccountMapper accountMapper,
                         AccountService accountService,
                         ConversationRepository conversationRepository) {
        this.mapper = modelMapper;
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.conversationRepository = conversationRepository;
    }

    public Message toEntity(MessageRequest messageRequest) {
        Account sender = accountService.getCurrentAccount();
        Conversation recipient = conversationRepository
                .findById(messageRequest.getConversationId())
                .orElseThrow();
        return new Message(sender, recipient, messageRequest.getContent(), LocalDateTime.now(), MessageStatus.SENT);
    }

    public MessageResponse toResponse(Message message) {
        MessageResponse messageResponse = mapper.map(message, MessageResponse.class);

        AccountResponse sender = accountMapper.toResponse(accountService.getCurrentAccount(), message.getSender());
        messageResponse.setSender(sender);
        messageResponse.setConversationId(message.getRecipient().getId());
        return messageResponse;
    }
}
