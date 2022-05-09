package com.vivacon.service.impl;

import com.vivacon.common.utility.PageableBuilder;
import com.vivacon.dto.request.Participants;
import com.vivacon.dto.response.OutlineConversation;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.Account;
import com.vivacon.entity.Conversation;
import com.vivacon.entity.Participant;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.mapper.ConversationMapper;
import com.vivacon.mapper.PageMapper;
import com.vivacon.repository.AccountRepository;
import com.vivacon.repository.ConversationRepository;
import com.vivacon.repository.ParticipantRepository;
import com.vivacon.service.AccountService;
import com.vivacon.service.ConversationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import static com.vivacon.common.constant.Constants.CONNECTED_CONVERSATION_NAME_TOKEN;

@Service
public class ConversationServiceImpl implements ConversationService {
    private ConversationRepository conversationRepository;
    private AccountRepository accountRepository;
    private ParticipantRepository participantRepository;
    private AccountService accountService;
    private ConversationMapper conversationMapper;

    public ConversationServiceImpl(ConversationRepository conversationRepository,
                                   AccountRepository accountRepository,
                                   ParticipantRepository participantRepository,
                                   AccountService accountService,
                                   ConversationMapper conversationMapper) {
        this.conversationRepository = conversationRepository;
        this.accountService = accountService;
        this.conversationMapper = conversationMapper;
        this.accountRepository = accountRepository;
        this.participantRepository = participantRepository;
    }

    @Override
    public OutlineConversation create(Participants participants) {
        Set<String> usernames = new TreeSet<>(participants.getUsernames());
        usernames = this.getAllParticipants(usernames);
        String conversationName = this.getConversationName(usernames);

        Conversation savedConversation = conversationRepository.save(new Conversation(conversationName));
        List<Account> accounts = new LinkedList<>();
        for (String username : usernames) {
            accounts.add(accountRepository.findByUsernameIgnoreCase(username).orElseThrow(RecordNotFoundException::new));
        }
        accounts.forEach(account -> participantRepository.save(new Participant(savedConversation, account)));

        return conversationMapper.toOutlineConversation(savedConversation);
    }

    /**
     * This function is used for creating a concrete name for the conversation based on the set of participant's names
     *
     * @param participants Set<String>
     * @return
     */
    private String getConversationName(Set<String> participants) {
        StringBuilder namingBuilder = new StringBuilder();
        participants.forEach(username -> namingBuilder.append(username).append(CONNECTED_CONVERSATION_NAME_TOKEN));
        namingBuilder.delete(namingBuilder.length() - CONNECTED_CONVERSATION_NAME_TOKEN.length(), namingBuilder.length());
        return namingBuilder.toString();
    }

    @Override
    public Set<String> getAllParticipants(Set<String> participantUsernames) {
        String principalUsername = accountService.getCurrentAccount().getUsername();
        if (!participantUsernames.contains(principalUsername)) {
            participantUsernames.add(principalUsername);
        }
        return participantUsernames;
    }

    @Override
    public PageDTO<OutlineConversation> findAllByCurrentAccount(Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Conversation.class);
        Page<Conversation> conversations = conversationRepository.findByPrincipalUsername(accountService.getCurrentAccount().getUsername(), pageable);
        return PageMapper.toPageDTO(conversations, conversation -> conversationMapper.toOutlineConversation(conversation));
    }

    @Override
    public PageDTO<OutlineConversation> findByRecipientUsername(String keyword, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Conversation.class);
        Page<Conversation> conversations = conversationRepository.findByApproximatelyName(keyword, accountService.getCurrentAccount().getUsername(), pageable);
        return PageMapper.toPageDTO(conversations, conversation -> conversationMapper.toOutlineConversation(conversation));
    }
}
