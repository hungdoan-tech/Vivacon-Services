package com.vivacon.service.impl;

import com.vivacon.common.utility.PageableBuilder;
import com.vivacon.dto.request.MessageRequest;
import com.vivacon.dto.request.NewParticipantMessage;
import com.vivacon.dto.request.TypingMessage;
import com.vivacon.dto.response.EssentialAccount;
import com.vivacon.dto.response.MessageResponse;
import com.vivacon.dto.request.UsualTextMessage;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.Account;
import com.vivacon.entity.Comment;
import com.vivacon.entity.Conversation;
import com.vivacon.entity.Message;
import com.vivacon.entity.MessageType;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.exception.UnauthorizedWebSocketException;
import com.vivacon.mapper.MessageMapper;
import com.vivacon.mapper.PageMapper;
import com.vivacon.repository.ConversationRepository;
import com.vivacon.repository.MessageRepository;
import com.vivacon.repository.ParticipantRepository;
import com.vivacon.service.AccountService;
import com.vivacon.service.MessageService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;

    private ConversationRepository conversationRepository;

    private ParticipantRepository participantRepository;

    private AccountService accountService;

    private MessageMapper messageMapper;

    public MessageServiceImpl(MessageRepository messageRepository,
                              ConversationRepository conversationRepository,
                              ParticipantRepository participantRepository,
                              AccountService accountService,
                              MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.messageMapper = messageMapper;
        this.accountService = accountService;
        this.participantRepository = participantRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {DataIntegrityViolationException.class, NonTransientDataAccessException.class, SQLException.class, Exception.class})
    @Override
    public MessageResponse save(UsualTextMessage messageRequest) {
        checkAuthorizedConversation(messageRequest.getConversationId());
        Message message = messageMapper.toEntity(messageRequest);
        Message savedMassage = this.messageRepository.save(message);
        conversationRepository.updateLastModifiedAtById(messageRequest.getConversationId(), LocalDateTime.now());
        return messageMapper.toResponse(savedMassage);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {DataIntegrityViolationException.class, NonTransientDataAccessException.class, SQLException.class, Exception.class})
    @Override
    public MessageResponse save(NewParticipantMessage messageRequest) {
        checkAuthorizedConversation(messageRequest.getConversationId());
        Message message = messageMapper.toEntity(messageRequest);
        Message savedMassage = this.messageRepository.save(message);
        conversationRepository.updateLastModifiedAtById(messageRequest.getConversationId(), LocalDateTime.now());
        return messageMapper.toResponse(savedMassage);
    }

    @Override
    public MessageResponse processTypingMessage(TypingMessage typingMessage) {
        checkAuthorizedConversation(typingMessage.getConversationId());
        EssentialAccount accountResponse = new EssentialAccount();
        accountResponse.setUsername(accountService.getCurrentAccount().getUsername());
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setSender(accountResponse);
        messageResponse.setContent(String.valueOf(typingMessage.isTyping()));
        messageResponse.setMessageType(MessageType.TYPING);
        return messageResponse;
    }

    private boolean checkAuthorizedConversation(long conversationId){
        Account principal = accountService.getCurrentAccount();
        participantRepository.findByConversationIdAndAccountId(conversationId, principal.getId()).orElseThrow(UnauthorizedWebSocketException::new);
        return true;
    }

    @Override
    public PageDTO<MessageResponse> findAllByConversationId(Long conversationId, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Message.class);
        Page<Message> pageMessage = messageRepository.findByRecipientId(conversationId, pageable);
        return PageMapper.toPageDTO(pageMessage, message -> messageMapper.toResponse(message));
    }
}
