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
//        sender.setId(messageInDto.getSender());
//        supposed to enter the id we get from the token when the user logs in

        KycUserProfile receiver = new KycUserProfile();
        receiver.setId((long) messageInDto.getReceiver());

        return Message.builder()
                .senderId(sender)
                .recieverId(receiver)
                .sentTimestamp(Instant.now())
                .messageBody(messageInDto.getMessage())
                .recieverStatus(0)
                .build();
    }
}
