package com.example.hijra_kyc.service;

import com.example.hijra_kyc.dto.MessageInDto;
import com.example.hijra_kyc.mapper.MessageMapper;
import com.example.hijra_kyc.model.Base;
import com.example.hijra_kyc.model.BaseList;
import com.example.hijra_kyc.model.Message;
import com.example.hijra_kyc.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private BaseService baseService;

    public Base<?> save(MessageInDto messageInDto){
        Message message = messageMapper.toMessage(messageInDto);

        try{
            var response=messageRepository.save(message);
            return baseService.success(response);
        }
        catch(Exception e){
            return baseService.error();
        }
    }

    public BaseList<?> getConversation(Long user1, Long user2){
        try{
           var result= messageRepository.findConversationBetweenUsers(user1,user2);
           return baseService.ListSuccess(result);
        }
        catch(Exception e){
            return baseService.listError();
        }
    }

}
