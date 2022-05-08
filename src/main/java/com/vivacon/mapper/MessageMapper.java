package com.vivacon.mapper;

import com.vivacon.dto.response.AccountResponse;
import com.vivacon.dto.response.MessageResponse;
import com.vivacon.entity.Message;
import com.vivacon.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    private ModelMapper mapper;

    private AccountMapper accountMapper;

    private AccountService accountService;

    public MessageMapper(ModelMapper modelMapper,
                         AccountMapper accountMapper,
                         AccountService accountService) {
        this.mapper = modelMapper;
    }

    public MessageResponse toResponse(Message message) {
        MessageResponse messageResponse = mapper.map(message, MessageResponse.class);

        AccountResponse sender = accountMapper.toResponse(accountService.getCurrentAccount(), message.getSender());
        messageResponse.setSender(sender);
        messageResponse.setConversationId(message.getRecipient().getId());

        return messageResponse;
    }
}
