package com.example.hijra_kyc.service;

import com.example.hijra_kyc.dto.MessageDelete;
import com.example.hijra_kyc.dto.MessageEdit;
import com.example.hijra_kyc.dto.MessageInDto;
import com.example.hijra_kyc.mapper.MessageMapper;
import com.example.hijra_kyc.model.Base;
import com.example.hijra_kyc.model.BaseList;
import com.example.hijra_kyc.model.Message;
import com.example.hijra_kyc.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private BaseService baseService;

    public Base<?> saveMessage(MessageInDto messageInDto){
        Message message = messageMapper.toMessage(messageInDto);

        try{
            messageRepository.findById(messageInDto.getReceiver())
                    .orElseThrow(()->new RuntimeException("Receiver Not Found"));

            var messageField=messageInDto.getMessage();
            if(messageField.isEmpty()){
                return baseService.error("Message Field Empty");
            }
            var response=messageRepository.save(message);
            return baseService.success(response);
        }
        catch(Exception e){
            return baseService.error(e.getMessage());
        }
    }

    public BaseList<?> getConversation(Long user){
        try{
           messageRepository.findById(user.intValue())
                   .orElseThrow(()->new RuntimeException("User Not Found"));
           var result= messageRepository.findConversationBetweenUsers(getLoggerUserId(),user);
           return baseService.ListSuccess(result);
        }
        catch(Exception e){
            return baseService.listError(e.getMessage());
        }
    }

    public Base<?> updateStatus(Long senderId) {
        try{
            Long receiverId = getLoggerUserId();
            messageRepository.findById(senderId.intValue())
                    .orElseThrow(()-> new RuntimeException("Sender Not Found"));
            messageRepository.updateStatus(senderId, receiverId);
            return baseService.success("Message Updated successfully");
        }
        catch(Exception e){
            return baseService.error(e.getMessage());
        }
    }

    public Base<?> deleteMessage(MessageDelete message) {
        try{
            messageRepository.findById(message.getId().intValue())
                    .orElseThrow(()-> new RuntimeException("Message Not Found"));
            if(message.getSenderId().intValue()==getLoggerUserId()){
                return baseService.error("Unauthorized!");
            }
            messageRepository.deleteById(message.getId().intValue());
            return baseService.success("Message Deleted successfully");
        }
        catch(Exception e){
            return baseService.error(e.getMessage());
        }
    }


    public Base<?> updateMessage(Long senderId, Long receiverId, MessageEdit message){
        try{
            var messageValue=messageRepository.findById(receiverId.intValue())
                    .orElseThrow(()->new RuntimeException("Message Not Found"));
            if(senderId.intValue()==getLoggerUserId()){
                return baseService.error("Unauthorized!");
            }
            var lastMessage=messageRepository.findLatestMessage(receiverId, getLoggerUserId());
            if(lastMessage==null){
                return baseService.error("Message Not Found");
            }
            messageValue.setMessageBody(message.getMessage());
            var updateResult=messageRepository.save(messageValue);
            return baseService.success(updateResult);
        }
        catch(Exception e){
            return baseService.error(e.getMessage());
        }
    }

    private static Long getLoggerUserId() {
//        some logic to get the currently logged in user
        return 1L;
    }



}
