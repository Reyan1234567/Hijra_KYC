package com.example.hijra_kyc.mapper;

import com.example.hijra_kyc.dto.MessageInDto;
import com.example.hijra_kyc.dto.MessageOutDto;
import com.example.hijra_kyc.model.Message;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class MessageMapper {
    public Message toMessage(MessageInDto messageInDto){
        return Message.builder()
                .sentTimestamp(Instant.now())
                .messageBody(messageInDto.getMessage())
                .recieverStatus(0)
                .build();
    }

    public MessageOutDto messageOutMapper(Message message) {
        return MessageOutDto.builder()
                .id(message.getId())
                .senderId(message.getSenderId().getId())
                .recieverId(message.getRecieverId().getId())
                .messageBody(message.getMessageBody())
                .sentTimestamp(message.getSentTimestamp())
                .recievedTimestamp(message.getReceivedTimestamp())
                .recieverStatus(message.getRecieverStatus())
                .build();
    }
}
