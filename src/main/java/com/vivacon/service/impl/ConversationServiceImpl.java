package com.vivacon.service.impl;

import com.vivacon.dto.request.Participants;
import com.vivacon.dto.response.ConversationResponse;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.service.ConversationService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ConversationServiceImpl implements ConversationService {

    @Override
    public ConversationResponse create(Participants participants) {
        return null;
    }

    @Override
    public Set<String> getAllParticipants(Set<String> usernames) {
        return null;
    }

    @Override
    public ConversationResponse findByRecipientUsername(String username) {
        return null;
    }

    @Override
    public PageDTO<ConversationResponse> findAllByCurrentAccount(Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        return null;
    }
}
