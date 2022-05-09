package com.vivacon.service;

import com.vivacon.dto.request.Participants;
import com.vivacon.dto.response.ConversationResponse;
import com.vivacon.dto.sorting_filtering.PageDTO;

import java.util.Optional;
import java.util.Set;

public interface ConversationService {
    ConversationResponse create(Participants participants);

    Set<String> getAllParticipants(Set<String> usernames);

    ConversationResponse findByRecipientUsername(String username);

    PageDTO<ConversationResponse> findAllByCurrentAccount(Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex);
}
