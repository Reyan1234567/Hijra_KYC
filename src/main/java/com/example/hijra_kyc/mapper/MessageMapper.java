package com.example.hijra_kyc.mapper;

import com.example.hijra_kyc.KycUserProfile;
import com.example.hijra_kyc.dto.MessageInDto;
import com.example.hijra_kyc.model.Message;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class MessageMapper {
    public Message toMessage(MessageInDto messageInDto){

        KycUserProfile sender = new KycUserProfile();
        sender.setId(messageInDto.getSender());

        KycUserProfile receiver = new KycUserProfile();
        receiver.setId(messageInDto.getReceiver());

        return Message.builder()
                .senderId(sender)
                .recieverId(receiver)
                .sentTimestamp(Instant.now())
                .messageBody(messageInDto.getMessage())
                .recieverStatus(0)
                .build();
    }
}
