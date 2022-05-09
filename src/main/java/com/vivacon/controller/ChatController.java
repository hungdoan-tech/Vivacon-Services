package com.vivacon.controller;

import com.vivacon.dto.request.MessageRequest;
import com.vivacon.dto.request.Participants;
import com.vivacon.dto.response.MessageResponse;
import com.vivacon.dto.response.OutlineConversation;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.service.ConversationService;
import com.vivacon.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import static com.vivacon.common.constant.Constants.API_V1;
import static com.vivacon.common.constant.Constants.PREFIX_USER_QUEUE_DESTINATION;
import static com.vivacon.common.constant.Constants.SUFFIX_CONVERSATION_QUEUE_DESTINATION;
import static com.vivacon.common.constant.Constants.SUFFIX_USER_QUEUE_NEW_CONVERSATION_DESTINATION;

@RestController
@Api(value = "Chatting endpoints")
@RequestMapping(API_V1)
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final ConversationService conversationService;

    public ChatController(SimpMessagingTemplate messagingTemplate,
                          MessageService messageService,
                          ConversationService conversationService) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
        this.conversationService = conversationService;
    }

    /**
     * This function is used to process chat message from current user to a conversation which he has involved,
     * it will save that valid message to database and publish that message to right the conversation channel
     *
     * @param messageRequest ChatMessageRequestBody
     */
    @MessageMapping("/chat")
    public void processChatMessage(@Payload @Valid MessageRequest messageRequest) {
        MessageResponse messageResponse = messageService.save(messageRequest);
        messagingTemplate.convertAndSendToUser(messageRequest.getConversationId().toString(), SUFFIX_CONVERSATION_QUEUE_DESTINATION, messageResponse);
    }

    /**
     * This function is used to create a new conversation based on the request from client which includes list of participant in this expected conversation
     *
     * @param participants Participants
     */
    @MessageMapping("/conversation")
    public void processCreatingConversation(@Payload @Valid Participants participants) {
        OutlineConversation conversation = conversationService.create(participants);
        Set<String> participantUsernames = new TreeSet<>(participants.getUsernames());
        participantUsernames = conversationService.getAllParticipants(participantUsernames);
        for (String username : participantUsernames) {
            String path = PREFIX_USER_QUEUE_DESTINATION + username + SUFFIX_USER_QUEUE_NEW_CONVERSATION_DESTINATION;
            messagingTemplate.convertAndSend(path, conversation);
        }
    }

    /**
     * This function is used to find all conversation of the current user
     *
     * @return PageDTO<ConversationResponse>
     */
    @ApiOperation(value = "Get all conversation of current user")
    @GetMapping("/conversation")
    public PageDTO<OutlineConversation> findConversationsOfCurrentUser(
            @RequestParam(value = "_order", required = false) Optional<String> order,
            @RequestParam(value = "_sort", required = false) Optional<String> sort,
            @RequestParam(value = "limit", required = false) Optional<Integer> pageSize,
            @RequestParam(value = "page", required = false) Optional<Integer> pageIndex) {
        return conversationService.findAllByCurrentAccount(order, sort, pageSize, pageIndex);
    }

    /**
     * This function is used to find all message in a specific conversation
     *
     * @param conversationId Long
     * @return PageDTO<MessageResponse>
     */
    @ApiOperation(value = "Get all messages in a specific conversation")
    @GetMapping("/conversation/{id}/messages")
    public PageDTO<MessageResponse> findMessagesInAConversation(
            @PathVariable(value = "id") Long conversationId,
            @RequestParam(value = "_order", required = false) Optional<String> order,
            @RequestParam(value = "_sort", required = false) Optional<String> sort,
            @RequestParam(value = "limit", required = false) Optional<Integer> pageSize,
            @RequestParam(value = "page", required = false) Optional<Integer> pageIndex) {
        return messageService.findAllByConversationId(conversationId, order, sort, pageSize, pageIndex);
    }

    /**
     * This function is used for checking if a conversation is existed between the current user and the request recipient
     *
     * @param username String
     * @return ConversationResponse
     */
    @ApiOperation(value = "Get the expected conversation between these two sender and recipient")
    @GetMapping("/conversation/search")
    public PageDTO<OutlineConversation> findConversationBasedOnRecipientUsername(
            @RequestParam("keyword") String username,
            @RequestParam(value = "_order", required = false) Optional<String> order,
            @RequestParam(value = "_sort", required = false) Optional<String> sort,
            @RequestParam(value = "limit", required = false) Optional<Integer> pageSize,
            @RequestParam(value = "page", required = false) Optional<Integer> pageIndex) {
        return conversationService.findByRecipientUsername(username, order, sort, pageSize, pageIndex);
    }
}
