package com.example.hijra_kyc.mapper;

import com.example.hijra_kyc.dto.MessageOutDto;
import com.example.hijra_kyc.model.Message;
import com.example.hijra_kyc.repository.UserRepository;
import com.example.hijra_kyc.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class MessageOutMapper {
    public MessageOutDto messageOutMapper(Message message) {
        return MessageOutDto.builder()
                .id(message.getId())
                .senderId(message.getSenderId().getId().intValue())
                .recieverId(message.getRecieverId().getId().intValue())
                .messageBody(message.getMessageBody())
                .sentTimestamp(message.getSentTimestamp())
                .recievedTimestamp(message.getReceivedTimestamp())
                .recieverStatus(message.getRecieverStatus())
                .build();
    }
}
