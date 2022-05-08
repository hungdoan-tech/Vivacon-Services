package com.vivacon.service;

import com.vivacon.dto.request.MessageRequest;
import com.vivacon.dto.response.MessageResponse;
import com.vivacon.dto.sorting_filtering.PageDTO;

import java.util.Optional;

public interface MessageService {
    MessageResponse save(MessageRequest messageRequest);

    PageDTO<MessageResponse> findAllByConversationId(Long conversationId, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex);
}
