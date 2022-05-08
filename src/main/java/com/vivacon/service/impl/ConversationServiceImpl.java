package com.vivacon.service.impl;

import com.vivacon.dto.request.Participants;
import com.vivacon.dto.response.ConversationResponse;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.Conversation;
import com.vivacon.mapper.PageMapper;
import com.vivacon.repository.ConversationRepository;
import com.vivacon.service.AccountService;
import com.vivacon.service.ConversationService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ConversationServiceImpl implements ConversationService {
    private ConversationRepository conversationRepository;
    private AccountService accountService;
    public ConversationServiceImpl(ConversationRepository conversationRepository,
                                   AccountService accountService){
        this.conversationRepository = conversationRepository;
        this.accountService = accountService;
    }

    @Override
    public ConversationResponse create(Participants participants) {
        return null;
    }

    @Override
    public Set<String> getAllParticipants(Set<String> usernames) {
        return null;
    }

    @Override
    public ConversationResponse findByRecipientUsername(String keyword) {
        Page<Conversation> conversations = conversationRepository.findByApproximatelyName(keyword, accountService.getCurrentAccount().getUsername(), null);
//        PageDTO<ConversationResponse> conversationResponse = PageDTOMapper.toPageDTO(conversations, entity -> {})
        return null;
    }

    @Override
    public PageDTO<ConversationResponse> findAllByCurrentAccount(Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        return null;
    }
}
