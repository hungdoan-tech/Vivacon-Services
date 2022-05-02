package com.vivacon.service.impl;

import com.vivacon.dto.request.MessageRequest;
import com.vivacon.dto.response.MessageResponse;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    @Override
    public MessageResponse save(MessageRequest messageRequest) {
        return null;
    }

    @Override
    public PageDTO<MessageResponse> findAllByConversationId(Long conversationId, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        return null;
    }
}
