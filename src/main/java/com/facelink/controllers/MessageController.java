package com.facelink.controllers;

import com.facelink.dto.ChatNotification;
import com.facelink.entity.ChatMessage;
import com.facelink.service.ChatMessageService;
import com.facelink.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
//@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
    @Autowired
    private FriendService friendService;

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @GetMapping("/message")
    public String message() {
        return "pages/message/index";
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(this.friendService.getAccountFriendDTO());
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        System.out.println("Payload....");
        ChatMessage savedMsg = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessage.getRecipientId()), "/queue/messages",
                new ChatNotification(
                        savedMsg.getId(),
                        savedMsg.getSenderId(),
                        savedMsg.getRecipientId(),
                        savedMsg.getContent()
                )
        );
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(@PathVariable Long senderId, @PathVariable Long recipientId) {
        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, recipientId));
    }
}
