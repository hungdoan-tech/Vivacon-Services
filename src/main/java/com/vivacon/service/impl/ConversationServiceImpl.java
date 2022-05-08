package com.vivacon.service.impl;

import com.vivacon.common.utility.PageableBuilder;
import com.vivacon.dto.request.Participants;
import com.vivacon.dto.response.OutlineConversation;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.Conversation;
import com.vivacon.mapper.ConversationMapper;
import com.vivacon.mapper.PageMapper;
import com.vivacon.repository.ConversationRepository;
import com.vivacon.service.AccountService;
import com.vivacon.service.ConversationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ConversationServiceImpl implements ConversationService {
    private ConversationRepository conversationRepository;
    private AccountService accountService;
    private ConversationMapper conversationMapper;

    public ConversationServiceImpl(ConversationRepository conversationRepository,
                                   AccountService accountService,
                                   ConversationMapper conversationMapper) {
        this.conversationRepository = conversationRepository;
        this.accountService = accountService;
        this.conversationMapper = conversationMapper;
    }

    @Override
    public OutlineConversation create(Participants participants) {
        return null;
    }

    @Override
    public Set<String> getAllParticipants(Set<String> usernames) {
        return null;
    }

    @Override
    public PageDTO<OutlineConversation> findAllByCurrentAccount(Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        return null;
    }

    @Override
    public PageDTO<OutlineConversation> findByRecipientUsername(String keyword, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Conversation.class);
        Page<Conversation> conversations = conversationRepository.findByApproximatelyName(keyword, accountService.getCurrentAccount().getUsername(), pageable);
        return PageMapper.toPageDTO(conversations, conversation -> conversationMapper.toOutlineConversation(conversation));
    }
}
