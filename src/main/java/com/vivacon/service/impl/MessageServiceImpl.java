package com.vivacon.service.impl;

import com.vivacon.common.utility.PageableBuilder;
import com.vivacon.dto.request.MessageRequest;
import com.vivacon.dto.response.MessageResponse;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.Message;
import com.vivacon.mapper.MessageMapper;
import com.vivacon.mapper.PageMapper;
import com.vivacon.repository.MessageRepository;
import com.vivacon.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;

    private MessageMapper messageMapper;

    public MessageServiceImpl(MessageRepository messageRepository,
                              MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    public MessageResponse save(MessageRequest messageRequest) {
        Message message = messageMapper.toEntity(messageRequest);
        return messageMapper.toResponse(this.messageRepository.save(message));
    }

    @Override
    public PageDTO<MessageResponse> findAllByConversationId(Long conversationId, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Message.class);
        Page<Message> pageMessage = messageRepository.findByRecipientId(conversationId, pageable);
        return PageMapper.toPageDTO(pageMessage, message -> messageMapper.toResponse(message));
    }
}
