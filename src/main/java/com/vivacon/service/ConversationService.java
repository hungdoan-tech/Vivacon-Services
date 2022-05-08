package com.vivacon.service;

import com.vivacon.dto.request.Participants;
import com.vivacon.dto.response.OutlineConversation;
import com.vivacon.dto.sorting_filtering.PageDTO;

import java.util.Optional;
import java.util.Set;

public interface ConversationService {
    OutlineConversation create(Participants participants);

    Set<String> getAllParticipants(Set<String> usernames);

    PageDTO<OutlineConversation> findAllByCurrentAccount(Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex);

    PageDTO<OutlineConversation> findByRecipientUsername(String keyword, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex);
}
