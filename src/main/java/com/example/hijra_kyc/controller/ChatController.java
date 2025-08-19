package com.example.hijra_kyc.controller;

import com.example.hijra_kyc.dto.MessageDto.MessageDisplayDto;
import com.example.hijra_kyc.dto.MessageDto.MessageInDto;
import com.example.hijra_kyc.dto.MessageDto.MessageOutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.Instant;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/broadcast")
    @SendTo("/topic/messages")
    public String broadcast(String message) {
        return message;
    }

    @MessageMapping("/private")
    public void SendPrivateMessage(Principal principal, MessageInDto message) {
        String sender = principal.getName();
        Long target = message.getReceiver();
        MessageDisplayDto messageToBeSent = MessageDisplayDto.builder()
                .content(message.getMessage())
                .sender(sender)
                .sentTimeStamp(Instant.now())
                .build();
        simpMessagingTemplate.convertAndSendToUser(target.toString(), "/queue/messages", messageToBeSent);
    }
}
