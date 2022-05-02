package com.vivacon.controller;

import com.vivacon.dto.request.MessageRequest;
import com.vivacon.dto.request.Participants;
import com.vivacon.dto.response.ConversationResponse;
import com.vivacon.dto.response.MessageResponse;
import com.vivacon.entity.Conversation;
import com.vivacon.entity.Message;
import com.vivacon.service.ConversationService;
import com.vivacon.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.vivacon.common.constant.Constants.PREFIX_USER_QUEUE_DESTINATION;
import static com.vivacon.common.constant.Constants.SUFFIX_CONVERSATION_QUEUE_DESTINATION;
import static com.vivacon.common.constant.Constants.SUFFIX_USER_QUEUE_NEW_CONVERSATION_DESTINATION;

@Controller
@Api(value = "Chatting APIs")
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService chatMessageService;
    private final ConversationService conversationService;

    public ChatController(SimpMessagingTemplate messagingTemplate,
                          MessageService messageService,
                          ConversationService conversationService) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessageService = messageService;
        this.conversationService = conversationService;
    }

    /**
     * This function is used to process chat message from current user to a conversation which he has involved,
     * it will save that valid message to database and publish that message to right the conversation channel
     *
     * @param chatMessageRequestBody ChatMessageRequestBody
     */
    @MessageMapping("/chat")
    public void processChatMessage(@Payload @Valid MessageRequest chatMessageRequestBody) {
        Message saved = chatMessageService.save(chatMessageRequestBody);
        MessageResponse messageBody = new MessageResponse(saved);
        messagingTemplate.convertAndSendToUser(chatMessageRequestBody.getConversationId().toString(), SUFFIX_CONVERSATION_QUEUE_DESTINATION, messageBody);
    }

    /**
     * This function is used to create a new conversation based on the request from client which includes list of participant in this expected conversation
     *
     * @param participants Participants
     */
    @MessageMapping("/conversations")
    public void processCreatingConversation(@Payload @Valid Participants participants) {
        Conversation conversation = conversationService.create(participants);
        Set<String> usernames = new HashSet<>(participants.getUsernames());
        usernames = conversationService.getAllParticipants(usernames);
        for (String username : usernames) {
            String path = PREFIX_USER_QUEUE_DESTINATION + username + SUFFIX_USER_QUEUE_NEW_CONVERSATION_DESTINATION;
            messagingTemplate.convertAndSend(path, conversation);
        }
    }

    /**
     * This function is used to find all conversation of the current user
     *
     * @return ResponseEntity<List < Conversation>>
     */
    @GetMapping("/api/conversations")
    @ApiOperation(value = "Get all conversation of current user")
    public ResponseEntity<Page<ConversationResponse>> findConversationsOfCurrentUser() {
        return ResponseEntity.ok(conversationService.findAllByAccount());
    }

    /**
     * This function is used to find all message in a specific conversation
     *
     * @param conversationId Long
     * @return ResponseEntity<List < ChatMessageResponseBody>>
     */
    @ApiOperation(value = "Get all messages in a specific conversation")
    @GetMapping("/api/conversations/{id}/messages")
    public ResponseEntity<Page<MessageResponse>> findMessagesInAConversation(
            @PathVariable("id") Long conversationId) {
        Page<Message> chatMessages = chatMessageService.findAllByConversation(conversationId);
        Page<MessageResponse> chatMessageBodies = chatMessages.stream().map(MessageResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(chatMessageBodies);
    }

    /**
     * This function is used for checking if a conversation is existed between the current user and the request recipient
     *
     * @param username String
     * @return ResponseEntity<Conversation>
     */
    @ApiOperation(value = "Get the expected conversation between these two sender and recipient")
    @GetMapping("/api/conversations/recipient")
    public ResponseEntity<Conversation> findConversationBasedOnRecipientUsername(@RequestParam("username") String username) {
        Conversation conversation = conversationService.findByRecipientUsername(username);
        return ResponseEntity.ok(conversation);
    }
}
